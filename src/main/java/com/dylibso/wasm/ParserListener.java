package com.dylibso.wasm;

import com.dylibso.wasm.types.Section;

public interface ParserListener {
    void onSection(Section section);
}
