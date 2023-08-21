package com.dylibso.wasm;

import com.dylibso.wasm.types.*;
import org.junit.Test;

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
        assertEquals(2, types.size());
        assertEquals("(I32) -> nil", types.get(0).toString());
        assertEquals("() -> nil", types.get(1).toString());

        // check import section
        var importSection = module.getImportSection();
        var imports = importSection.getImports();
        assertEquals(1, imports.size());
        assertEquals("func[0] <env.gotit>", imports.get(0).toString());

        // check data section
        var dataSection = module.getDataSection();
        var dataSegments = dataSection.getDataSegments();
        assertEquals(1, dataSegments.size());
        var segment = dataSegments.get(0);
        assertEquals(0, segment.getIdx());
        assertEquals(16L, (long) segment.getOffset().get(1));
        assertArrayEquals(new byte[]{0x00,0x01,0x02,0x03}, segment.getData());

        // check start section
        var startSection = module.getStartSection();
        assertEquals(1, startSection.getStartIndex());

        // check function section
        var funcSection = module.getFunctionSection();
        var typeIndices = funcSection.getTypeIndices();
        assertEquals(1, typeIndices.size());
        assertEquals(1L, (long)typeIndices.get(0));

        // check export section
//        var exportSection = module.getExportSection();
//        var exports = exportSection.getExports();
//        assertEquals(1, exports.size());

        // check memory section
        var memorySection = module.getMemorySection();
        var memories = memorySection.getMemories();
        assertEquals(1, memories.size());
        assertEquals(1, memories.get(0).getLimitMin());
        assertNull(memories.get(0).getLimitMax());

        var codeSection = module.getCodeSection();
        var functionBodies = codeSection.getFunctionBodies();
        assertEquals(1, functionBodies.size());
        var func = functionBodies.get(0);
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
        assertEquals(1, types.size());
        assertEquals("(I32) -> I32", types.get(0).toString());

        // check function section
        var funcSection = module.getFunctionSection();
        var typeIndices = funcSection.getTypeIndices();
        assertEquals(1, typeIndices.size());
        assertEquals(0L, (long)typeIndices.get(0));

        var codeSection = module.getCodeSection();
        var functionBodies = codeSection.getFunctionBodies();
        assertEquals(1, functionBodies.size());
        var func = functionBodies.get(0);
        var locals = func.getLocals();
        assertEquals(1, locals.size());
        assertEquals(1, locals.get(0).asInt());
        var instructions = func.getInstructions();
        assertEquals(23, instructions.size());
    }

    @Test
    public void shouldParseAllFiles() {
        var parser = new Parser("src/test/resources/wasm/kitchensink.wat.wasm");
        var module = parser.parseModule();
        parser = new Parser("src/test/resources/wasm/host-function.wat.wasm");
        module = parser.parseModule();
        parser = new Parser("src/test/resources/wasm/branching.wat.wasm");
        module = parser.parseModule();
        parser = new Parser("src/test/resources/wasm/br_if.wat.wasm");
        module = parser.parseModule();
        parser = new Parser("src/test/resources/wasm/globals.wat.wasm");
        module = parser.parseModule();
        parser = new Parser("src/test/resources/wasm/memories.wat.wasm");
        module = parser.parseModule();
        parser = new Parser("src/test/resources/wasm/trap.wat.wasm");
        module = parser.parseModule();
    }

    @Test
    public void shouldParseCodeWasm() {
        var parser = new Parser("src/test/resources/wasm/code.wasm");
        var module = parser.parseModule();
        assertEquals(9, module.getCustomSections().size());
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
        var fbody = codeSection.getFunctionBodies().get(0);
        var ast = fbody.getAst();
        ast.print();
    }
}
