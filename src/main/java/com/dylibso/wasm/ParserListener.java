package com.dylibso.wasm;

import com.dylibso.wasm.types.Section;

public interface ParserListener {
    public void onSection(Section s);
}
