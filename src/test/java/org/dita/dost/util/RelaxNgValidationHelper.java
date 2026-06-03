/*
 * This file is part of the DITA Open Toolkit project.
 *
 * Copyright 2026 Jarno Elovirta
 *
 * See the accompanying LICENSE file for applicable license.
 */
package org.dita.dost.util;

import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.ValidateProperty;
import com.thaiopensource.validate.ValidationDriver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class RelaxNgValidationHelper {

  private RelaxNgValidationHelper() {}

  public static void validate(File ditaDir, String schemaUri, File document) throws Exception {
    CatalogUtils.setDitaDir(ditaDir.getAbsoluteFile());
    final ValidationErrorHandler errorHandler = new ValidationErrorHandler();
    final PropertyMapBuilder properties = new PropertyMapBuilder();
    properties.put(ValidateProperty.ERROR_HANDLER, errorHandler);
    properties.put(ValidateProperty.ENTITY_RESOLVER, CatalogUtils.getCatalogResolver());
    properties.put(ValidateProperty.URI_RESOLVER, CatalogUtils.getCatalogResolver());

    final ValidationDriver driver = new ValidationDriver(properties.toPropertyMap());
    if (!driver.loadSchema(inputSource(schemaUri)) || errorHandler.hasErrors()) {
      throw new ValidationFailure("Failed to load RELAX NG schema " + schemaUri + ": " + errorHandler.format());
    }
    if (!driver.validate(inputSource(document)) || errorHandler.hasErrors()) {
      throw new ValidationFailure("Failed to validate " + document + " against " + schemaUri + ": " + errorHandler.format());
    }
  }

  private static InputSource inputSource(String systemId) {
    final InputSource inputSource = new InputSource(systemId);
    inputSource.setSystemId(systemId);
    return inputSource;
  }

  private static InputSource inputSource(File file) {
    final String systemId = file.toURI().toString();
    final InputSource inputSource = new InputSource(systemId);
    inputSource.setSystemId(systemId);
    return inputSource;
  }

  public static class ValidationFailure extends Exception {

    public ValidationFailure(String message) {
      super(message);
    }
  }

  private static final class ValidationErrorHandler implements ErrorHandler {

    private final List<String> messages = new ArrayList<>();

    private boolean hasErrors() {
      return !messages.isEmpty();
    }

    private String format() {
      return String.join(" | ", messages);
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
      messages.add(format("warning", exception));
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
      messages.add(format("error", exception));
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
      messages.add(format("fatal", exception));
    }

    private String format(String level, SAXParseException exception) {
      final String systemId = exception.getSystemId() != null ? exception.getSystemId() : "";
      return level + ":" + systemId + ":" + exception.getLineNumber() + ":" + exception.getColumnNumber() + ":" + exception.getMessage();
    }
  }
}
