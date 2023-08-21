package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class ElementSection extends Section {
    private List<Element> elements;

    public ElementSection(long id, long size) {
       super(id, size);
       this.elements = new ArrayList<>();
    }

    public void addElement(Element e) {
        this.elements.add(e);
    }

    public List<Element> getElements() {
        return elements;
    }
}
