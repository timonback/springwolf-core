// SPDX-License-Identifier: Apache-2.0
package io.github.springwolf.core.schemas.example;

import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

public interface ExampleXmlValueSerializer {

    String writeDocumentAsXmlString(Document document) throws TransformerException;
}