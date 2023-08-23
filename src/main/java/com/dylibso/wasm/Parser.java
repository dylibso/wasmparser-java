package com.dylibso.wasm;

import com.dylibso.wasm.types.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private String filePath;
    private ParserListener listener;
    private List<Integer> includeSections;

    private static int MAGIC_BYTES = 1836278016; // Magic prefix \0asm

    public Parser(String filePath) {
        this.filePath = filePath;
        this.listener = null;
        this.includeSections = null;
    }

    public void setListener(ParserListener listener) {
        this.listener = listener;
    }

    public void includeSection(int sectionId) {
        if (this.includeSections == null) this.includeSections = new ArrayList<>();
        this.includeSections.add(sectionId);
    }

    private ByteBuffer readByteBuffer() {
        try {
            // Read the Wasm file into a ByteBuffer
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] buf = new byte[fileInputStream.available()];
            fileInputStream.read(buf);
            fileInputStream.close();

            var buffer = ByteBuffer.wrap(buf);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to read file " + filePath);
        }
    }

    public Module parseModule() {
        if (this.listener != null) {
            throw new IllegalArgumentException("This method overrides your custom listener. You probably want to use Module#parse() instead");
        }
        var builder = new ModuleBuilder();
        setListener(builder);
        parse();
        return builder.getModule();
    }

    public void parse() {
        if (this.listener == null) {
            throw new IllegalArgumentException("Missing ParserListener. Use Module#setListener to set a listener;");
        }

        var buffer = readByteBuffer();

        int magicNumber = buffer.getInt();
        assert magicNumber == MAGIC_BYTES;
        int version = buffer.getInt();
        assert version == 1;

        while (buffer.hasRemaining()) {
            var sectionId = (int) readVarUInt32(buffer);
            var sectionSize = readVarUInt32(buffer);

            if (shouldParseSection(sectionId)) {
                // Process different section types based on the sectionId
                switch (sectionId) {
                    case SectionId.CUSTOM -> {
                        var customSection = parseCustomSection(buffer, sectionId, sectionSize);
                        listener.onSection(customSection);
                    }
                    case SectionId.TYPE -> {
                        var typeSection = parseTypeSection(buffer, sectionId, sectionSize);
                        listener.onSection(typeSection);
                    }
                    case SectionId.IMPORT -> {
                        var importSection = parseImportSection(buffer, sectionId, sectionSize);
                        listener.onSection(importSection);
                    }
                    case SectionId.FUNCTION -> {
                        var funcSection = parseFunctionSection(buffer, sectionId, sectionSize);
                        listener.onSection(funcSection);
                    }
                    case SectionId.TABLE-> {
                        var tableSection = parseTableSection(buffer, sectionId, sectionSize);
                        listener.onSection(tableSection);
                    }
                    case SectionId.MEMORY -> {
                        var memorySection = parseMemorySection(buffer, sectionId, sectionSize);
                        listener.onSection(memorySection);
                    }
                    case SectionId.GLOBAL -> {
                        var globalSection = parseGlobalSection(buffer, sectionId, sectionSize);
                        listener.onSection(globalSection);
                    }
                    case SectionId.EXPORT -> {
                        var exportSection = parseExportSection(buffer, sectionId, sectionSize);
                        listener.onSection(exportSection);
                    }
                    case SectionId.START -> {
                        var startSection = parseStartSection(buffer, sectionId, sectionSize);
                        listener.onSection(startSection);
                    }
                    case SectionId.ELEMENT -> {
                        var elementSection = parseElementSection(buffer, sectionId, sectionSize);
                        listener.onSection(elementSection);
                    }
                    case SectionId.CODE -> {
                        var codeSection = parseCodeSection(buffer, sectionId, sectionSize);
                        listener.onSection(codeSection);
                    }
                    case SectionId.DATA -> {
                        var dataSection = parseDataSection(buffer, sectionId, sectionSize);
                        listener.onSection(dataSection);
                    }
                    default -> {
                        System.out.println("Skipping Unknown Section with ID: " + sectionId);
                        buffer.position((int) (buffer.position() + sectionSize));
                    }
                }
            } else {
                System.out.println("Skipping Section with ID due to configuration: " + sectionId);
                buffer.position((int) (buffer.position() + sectionSize));
            }
        }
    }

    private boolean shouldParseSection(int sectionId) {
        if (this.includeSections == null) return true;
        if (this.includeSections.contains(sectionId)) return true;
        return false;
    }

    private static CustomSection parseCustomSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var customSection = new CustomSection(sectionId, sectionSize);
        var name = readName(buffer);
        customSection.setName(name);
        var byteLen = name.getBytes().length;
        var bytes = new byte[(int) (sectionSize - byteLen - Leb128.computeLeb128Size(byteLen))];
        buffer.get(bytes);
        customSection.setBytes(bytes);
        return customSection;
    }

    private static TypeSection parseTypeSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var typeSection = new TypeSection(sectionId, sectionSize);
        var typeCount = readVarUInt32(buffer);

        // Parse individual types in the type section
        for (int i = 0; i < typeCount; i++) {
            var form = readVarUInt32(buffer);

            if (form != 0x60) {
                throw new RuntimeException("We don't support non func types. Form " + String.format("0x%02X", form) + " was given but we expected 0x60");
            }

            // Parse function types (form = 0x60)
            var paramCount = (int) readVarUInt32(buffer);
            var params = new ValueType[paramCount];

            // Parse parameter types
            for (int j = 0; j < paramCount; j++) {
                params[j] = ValueType.byId(readVarUInt32(buffer));
            }

            var returnCount = (int) readVarUInt32(buffer);
            var returns = new ValueType[returnCount];

            // Parse return types
            for (int j = 0; j < returnCount; j++) {
                returns[j] = ValueType.byId(readVarUInt32(buffer));
            }

            typeSection.addType(new FunctionType(params, returns));
        }

        return typeSection;
    }

    private static ImportSection parseImportSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var importSection = new ImportSection(sectionId, sectionSize);
        var importCount = readVarUInt32(buffer);

        // Parse individual imports in the import section
        for (int i = 0; i < importCount; i++) {
            String moduleName = readName(buffer);
            String fieldName = readName(buffer);
            var descType = ImportDescType.byId(readVarUInt32(buffer));
            var descIdx = readVarUInt32(buffer);
            var desc = new ImportDesc(descIdx, descType);
            importSection.addImport(new Import(moduleName, fieldName, desc));
        }

        return importSection;
    }

    private static FunctionSection parseFunctionSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var functionSection = new FunctionSection(sectionId, sectionSize);
        var functionCount = readVarUInt32(buffer);

        // Parse individual functions in the function section
        for (int i = 0; i < functionCount; i++) {
            var typeIndex = readVarUInt32(buffer);
            functionSection.addTypeIndex((int) typeIndex);
        }

        return functionSection;
    }

    private static TableSection parseTableSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var tableSection = new TableSection(sectionId, sectionSize);
        var tableCount = readVarUInt32(buffer);

        // Parse individual functions in the function section
        for (int i = 0; i < tableCount; i++) {
            var tableType = ElementType.byId(readVarUInt32(buffer));
            var limitType = readVarUInt32(buffer);
            assert limitType == 0x00 || limitType == 0x01;
            var min = readVarUInt32(buffer);
            Long max = null;
            if (limitType == 0x01) {
                max = readVarUInt32(buffer);
            }
            tableSection.addTable(new Table(tableType, min, max));
        }

        return tableSection;
    }

    private static MemorySection parseMemorySection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var memorySection = new MemorySection(sectionId, sectionSize);
        var memoryCount = readVarUInt32(buffer);

        // Parse individual functions in the function section
        for (int i = 0; i < memoryCount; i++) {
            var limitType = readVarUInt32(buffer);
            assert limitType == 0x00 || limitType == 0x01;
            var min = readVarUInt32(buffer);
            Long max = null;
            if (limitType == 0x01) {
                max = readVarUInt32(buffer);
            }
            memorySection.addMemory(new Memory(min, max));
        }

        return memorySection;
    }

    private static GlobalSection parseGlobalSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var globalSection = new GlobalSection(sectionId, sectionSize);
        var globalCount = readVarUInt32(buffer);

        // Parse individual globals
        for (int i = 0; i < globalCount; i++) {
            var valueType = ValueType.byId(readVarUInt32(buffer));
            var mutabilityType = MutabilityType.byId(readVarUInt32(buffer));
            var init = new ArrayList<Byte>();
            // TODO make it instruction parser
            byte b;
            do {
                b = (byte) (buffer.get() & 0xff);
                init.add(b);
            } while (b != 0x0B);
            globalSection.addGlobal(new Global(valueType, mutabilityType, init));
        }


        return globalSection;
    }

    private static ExportSection parseExportSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var exportSection = new ExportSection(sectionId, sectionSize);
        var exportCount = readVarUInt32(buffer);

        // Parse individual functions in the function section
        for (int i = 0; i < exportCount; i++) {
            var name = readName(buffer);
            var exportType = ExportDescType.byId(readVarUInt32(buffer));
            var index = readVarUInt32(buffer);
            var desc = new ExportDesc(index, exportType);
            exportSection.addExport(new Export(name, desc));
        }

        return exportSection;
    }

    private static StartSection parseStartSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var startSection = new StartSection(sectionId, sectionSize);
        startSection.setStartIndex(readVarUInt32(buffer));
        return startSection;
    }

    private static ElementSection parseElementSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var elementSection = new ElementSection(sectionId, sectionSize);
        var elementCount = readVarUInt32(buffer);

        for (var i = 0; i < elementCount; i++) {
            var tableIndex = readVarUInt32(buffer);
            var expr = new ArrayList<Byte>();
            byte b;
            do {
                b = (byte) (buffer.get() & 0xff);
                expr.add(b);
            } while (b != 0x0B);
            var funcIndexCount = readVarUInt32(buffer);
            var funcIndices = new ArrayList<Long>();
            for (var j = 0; j < funcIndexCount; j++) {
                funcIndices.add(readVarUInt32(buffer));
            }
            elementSection.addElement(new Element(tableIndex, expr, funcIndices));

        }

        return elementSection;
    }

    private static CodeSection parseCodeSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var codeSection = new CodeSection(sectionId, sectionSize);
        var funcBodyCount = readVarUInt32(buffer);

        // Parse individual function bodies in the code section
        for (int i = 0; i < funcBodyCount; i++) {
            var funcEndPoint = readVarUInt32(buffer) + buffer.position();
            var localCount = readVarUInt32(buffer);
            var locals = new ArrayList<Value>();
            for (int j = 0; j < localCount; j++) {
                var bytes = readVarUInt32(buffer);
                var type = ValueType.byId(readVarUInt32(buffer));
                locals.add(new Value(type, bytes));
            }
            var instructions = new ArrayList<Instruction>();
            OpCode op;
            do {
                var instruction = parseInstruction(buffer);
                instructions.add(instruction);
                //System.out.println(Integer.toHexString(instruction.getAddress()) + " " + instruction);
            } while (buffer.position() < funcEndPoint);
            codeSection.addFunctionBody(new FunctionBody(locals, instructions));
        }

        return codeSection;
    }

    private static Instruction parseInstruction(ByteBuffer buffer) {
        var address = buffer.position();
        var b = buffer.get() & 0xff;
        var op = OpCode.byOpCode(b);
        if (op == null) {
            throw new IllegalArgumentException("Can't find opcode for op value " + b);
        }
        //System.out.println("b: " + b + " op: " + op);
        var signature = OpCode.getSignature(op);
        if (signature.length == 0) {
            return new Instruction(address, op, List.of());
        }
        var operands = new ArrayList<Long>();
        for (var sig : signature) {
            switch (sig) {
                case VARUINT -> operands.add(readVarUInt32(buffer));
                case FLOAT64 -> operands.add(readFloat64(buffer));
                case FLOAT32 -> operands.add(readFloat32(buffer));
                case VEC_VARUINT -> {
                    var vcount = readVarUInt32(buffer);
                    for (var i = 0; i < vcount; i++) {
                        operands.add(readVarUInt32(buffer));
                    }
                }
            }
        }
        return new Instruction(address, op, operands);
    }

    private static DataSection parseDataSection(ByteBuffer buffer, long sectionId, long sectionSize) {
        var dataSection = new DataSection(sectionId, sectionSize);
        var dataSegmentCount = readVarUInt32(buffer);

        for (var i = 0; i < dataSegmentCount; i++) {
            var idx = readVarUInt32(buffer);
            // TODO make it instruction parser
            var offset = new ArrayList<Byte>();
            byte b;
            do {
                b = (byte) (buffer.get() & 0xff);
                offset.add(b);
            } while (b != 0x0B);
            byte[] data = new byte[(int) readVarUInt32(buffer)];
            buffer.get(data);
            dataSection.addDataSegment(new DataSegment(idx, offset, data));
        }

        return dataSection;
    }

    /**
     * Parse a varuint32 from the buffer. We can't fit an unsigned 32bit int
     * into a java int, so we must use a long.
     *
     * @param buffer
     * @return
     */
    private static long readVarUInt32(ByteBuffer buffer) {
        return Leb128.readUnsignedLeb128(buffer);
    }

    // TODO read real float
    // should also distinguish b/w f32 and f64
    private static long readFloat64(ByteBuffer buffer) {
        return buffer.getLong();
    }
    private static long readFloat32(ByteBuffer buffer) {
        return buffer.getInt();
    }

    private static String readName(ByteBuffer buffer) {
        var length = (int) readVarUInt32(buffer);
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
