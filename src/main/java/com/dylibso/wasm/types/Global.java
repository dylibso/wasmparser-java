package com.dylibso.wasm.types;

import java.util.List;

public class Global {
    private ValueType valueType;
    private MutabilityType mutabilityType;
    private List<Byte> init;

    public Global(ValueType valueType, MutabilityType mutabilityType, List<Byte> init) {
        this.valueType = valueType;
        this.mutabilityType = mutabilityType;
        this.init = init;
    }

    public MutabilityType getMutabilityType() {
        return mutabilityType;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public List<Byte> getInit() {
        return init;
    }
}
