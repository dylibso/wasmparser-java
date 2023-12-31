package com.dylibso.wasm;

import com.dylibso.wasm.types.*;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class ParserTest {

    @Test
    public void shouldParseFile() {
        var parser = new Parser("src/test/resources/wasm/start.wat.wasm");
        var module = parser.parseModule();

        // check types section
        var typeSection = module.getTypeSection();
        var types = typeSection.getTypes();
        assertEquals(2, types.length);
        assertEquals("(I32) -> nil", types[0].toString());
        assertEquals("() -> nil", types[1].toString());

        // check import section
        var importSection = module.getImportSection();
        var imports = importSection.getImports();
        assertEquals(1, imports.length);
        assertEquals("func[0] <env.gotit>", imports[0].toString());

        // check data section
        var dataSection = module.getDataSection();
        var dataSegments = dataSection.getDataSegments();
        assertEquals(1, dataSegments.length);
        var segment = dataSegments[0];
        assertEquals(0, segment.getIdx());
        assertEquals(OpCode.I32_CONST, segment.getOffset()[0].getOpcode());
        assertArrayEquals(new byte[]{0x00,0x01,0x02,0x03}, segment.getData());

        // check start section
        var startSection = module.getStartSection();
        assertEquals(1, startSection.getStartIndex());

        // check function section
        var funcSection = module.getFunctionSection();
        var typeIndices = funcSection.getTypeIndices();
        assertEquals(1, typeIndices.length);
        assertEquals(1L, (long)typeIndices[0]);

        // check export section
//        var exportSection = module.getExportSection();
//        var exports = exportSection.getExports();
//        assertEquals(1, exports.size());

        // check memory section
        var memorySection = module.getMemorySection();
        var memories = memorySection.getMemories();
        assertEquals(1, memories.length);
        assertEquals(1, memories[0].getLimitMin());
        assertNull(memories[0].getLimitMax());

        var codeSection = module.getCodeSection();
        var functionBodies = codeSection.getFunctionBodies();
        assertEquals(1, functionBodies.length);
        var func = functionBodies[0];
        var locals = func.getLocals();
        assertEquals(0, locals.size());
        var instructions = func.getInstructions();
        assertEquals(3, instructions.size());

        assertEquals("I32_CONST [42]", instructions.get(0).toString());
        assertEquals(OpCode.I32_CONST, instructions.get(0).getOpcode());
        assertEquals(42L, (long)instructions.get(0).getOperands().get(0));
        assertEquals(OpCode.CALL, instructions.get(1).getOpcode());
        assertEquals(0L, (long)instructions.get(1).getOperands().get(0));
        assertEquals(OpCode.END, instructions.get(2).getOpcode());
    }

    @Test
    public void shouldParseIterfact() {
        var parser = new Parser("src/test/resources/wasm/iterfact.wat.wasm");
        var module = parser.parseModule();

        // check types section
        var typeSection = module.getTypeSection();
        var types = typeSection.getTypes();
        assertEquals(1, types.length);
        assertEquals("(I32) -> I32", types[0].toString());

        // check function section
        var funcSection = module.getFunctionSection();
        var typeIndices = funcSection.getTypeIndices();
        assertEquals(1, typeIndices.length);
        assertEquals(0L, (long)typeIndices[0]);

        var codeSection = module.getCodeSection();
        var functionBodies = codeSection.getFunctionBodies();
        assertEquals(1, functionBodies.length);
        var func = functionBodies[0];
        var locals = func.getLocals();
        assertEquals(1, locals.size());
        assertEquals(1, locals.get(0).asInt());
        var instructions = func.getInstructions();
        assertEquals(23, instructions.size());
    }

    @Test
    public void shouldParseAllFiles() {
        File dir = new File("src/test/resources/wasm/");
        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".wasm"));
        for (var f : files) {
            var parser = new Parser(f.getPath());
            var module = parser.parseModule();
        }
    }

    @Test
    public void shouldSupportCustomListener() {
        var parser = new Parser("src/test/resources/wasm/code.wasm");
        parser.includeSection(SectionId.CUSTOM);
        parser.setListener(s -> {
            if (s.getSectionId() == SectionId.CUSTOM) {
                var customSection = (CustomSection) s;
                var name = customSection.getName();
                assertTrue(name.length() > 0);
            } else {
                fail("Should not have received section with id: " + s.getSectionId());
            }
        });
        parser.parse();
    }

    @Test
    public void shouldParseAst() {
        var parser = new Parser("src/test/resources/wasm/code.wasm");
        var module = parser.parseModule();
        var codeSection = module.getCodeSection();
        var fbody = codeSection.getFunctionBodies()[0];
        var ast = fbody.getAst();
        ast.print();
    }

    @Test
    public void shouldParseFloats() {
        var parser = new Parser("src/test/resources/wasm/float.wat.wasm");
        var module = parser.parseModule();
        var codeSection = module.getCodeSection();
        var fbody = codeSection.getFunctionBodies()[0];
        var f32 = Encoding.longToFloat(fbody.getInstructions().get(0).getOperands().get(0));
        assertEquals(0.12345678f, f32, 0.000000001f);
        var f64 = Encoding.longToDouble(fbody.getInstructions().get(1).getOperands().get(0));
        assertEquals(0.123456789012345d, f64, 0.00000000000001d);
    }


    @Test
    public void shouldProperlyParseSignedValue() {
        var parser = new Parser("src/test/resources/wasm/i32.wat.wasm");
        var module = parser.parseModule();
        var codeSection = module.getCodeSection();
        var fbody = codeSection.getFunctionBodies()[0];
        assertEquals(-2147483648L, (long) fbody.getInstructions().get(0).getOperands().get(0));
        assertEquals(0L, (long) fbody.getInstructions().get(2).getOperands().get(0));
        assertEquals(2147483647L, (long) fbody.getInstructions().get(4).getOperands().get(0));
        assertEquals(-9223372036854775808L, (long) fbody.getInstructions().get(6).getOperands().get(0));
        assertEquals(0L, (long) fbody.getInstructions().get(8).getOperands().get(0));
        assertEquals(9223372036854775807L, (long) fbody.getInstructions().get(10).getOperands().get(0));
        assertEquals(-2147483647L, (long) fbody.getInstructions().get(12).getOperands().get(0));
        assertEquals(2147483646L, (long) fbody.getInstructions().get(14).getOperands().get(0));
        assertEquals(-9223372036854775807L, (long) fbody.getInstructions().get(16).getOperands().get(0));
        assertEquals(9223372036854775806L, (long) fbody.getInstructions().get(18).getOperands().get(0));
        assertEquals(-1L, (long) fbody.getInstructions().get(20).getOperands().get(0));
        assertEquals(1L, (long) fbody.getInstructions().get(22).getOperands().get(0));
        assertEquals(-1L, (long) fbody.getInstructions().get(24).getOperands().get(0));
        assertEquals(1L, (long) fbody.getInstructions().get(26).getOperands().get(0));
    }
}
