/*
 * Copyright 2014 Joseph Moore
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thatjoemoore.utils.annotations;

import com.google.auto.common.MoreElements;

import javax.annotation.Nonnull;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;

import static java.util.Objects.requireNonNull;

/**
 * Created by adm.jmooreoa on 12/30/14.
 */
public class Logger {
    private final Messager messager;

    public Logger(@Nonnull Messager messager) {
        this.messager = requireNonNull(messager, "messager cannot be null");
    }
    
    //================================
    // fatal level
    //================================

    /**
     * Log a message at the fatal level, then throws an AbortProcessingException.
     *
     * @param message message text
     */
    public void fatal(@Nonnull CharSequence message) {
        fatal(message, null, null, null);
    }

    /**
     * Log a message at the fatal level, then throws an AbortProcessingException.
     *
     * @param message message text
     * @param elem    element this message is about
     */
    public void fatal(@Nonnull CharSequence message, Element elem) {
        fatal(message, elem, null, null);
    }

    /**
     * Log a message at the fatal level, then throws an AbortProcessingException.
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation type (on the element) this message is about
     */
    public void fatal(@Nonnull CharSequence message, Element elem, Class<? extends Annotation> annotation) {
        fatal(message, elem,
                annotation != null && elem != null ? MoreElements.getAnnotationMirror(elem, annotation).orNull() : null, null);
    }

    /**
     * Log a message at the fatal level, then throws an AbortProcessingException.
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void fatal(@Nonnull CharSequence message, Element elem, Annotation annotation) {
        fatal(message, elem,
                elem != null && annotation != null ? MoreElements.getAnnotationMirror(elem, annotation.getClass()).orNull() : null, null);
    }

    /**
     * Log a message at the fatal level, then throws an AbortProcessingException.
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void fatal(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation) {
        fatal(message, elem, annotation, null);
    }

    /**
     * Log a message at the fatal level, then throws an AbortProcessingException.
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     * @param value      annotation value this message is about
     */
    public void fatal(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(Diagnostic.Kind.ERROR, requireNonNull(message, "message cannot be null"), elem, annotation, value);
        throw new AbstractProcessorExt.AbortProcessingException(message.toString(), elem, annotation, value);
    }

    //================================
    // error level
    //================================

    /**
     * Log a message at the error level
     *
     * @param message message text
     */
    public void error(@Nonnull CharSequence message) {
        error(message, null, null, null);
    }

    /**
     * Log a message at the error level
     *
     * @param message message text
     * @param elem    element this message is about
     */
    public void error(@Nonnull CharSequence message, Element elem) {
        error(message, elem, null, null);
    }

    /**
     * Log a message at the error level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation type (on the element) this message is about
     */
    public void error(@Nonnull CharSequence message, Element elem, Class<? extends Annotation> annotation) {
        error(message, elem,
                annotation != null && elem != null ? MoreElements.getAnnotationMirror(elem, annotation).orNull() : null, null);
    }

    /**
     * Log a message at the error level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void error(@Nonnull CharSequence message, Element elem, Annotation annotation) {
        error(message, elem,
                elem != null && annotation != null ? MoreElements.getAnnotationMirror(elem, annotation.getClass()).orNull() : null, null);
    }

    /**
     * Log a message at the error level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void error(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation) {
        error(message, elem, annotation, null);
    }

    /**
     * Log a message at the error level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     * @param value      annotation value this message is about
     */
    public void error(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(Diagnostic.Kind.ERROR, requireNonNull(message, "message cannot be null"), elem, annotation, value);
    }
    

    //================================
    // warning level
    //================================

    /**
     * Log a message at the warning level
     *
     * @param message message text
     */
    public void warning(@Nonnull CharSequence message) {
        warning(message, null, null, null);
    }

    /**
     * Log a message at the warning level
     *
     * @param message message text
     * @param elem    element this message is about
     */
    public void warning(@Nonnull CharSequence message, Element elem) {
        warning(message, elem, null, null);
    }

    /**
     * Log a message at the warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation type (on the element) this message is about
     */
    public void warning(@Nonnull CharSequence message, Element elem, Class<? extends Annotation> annotation) {
        warning(message, elem,
                annotation != null && elem != null ? MoreElements.getAnnotationMirror(elem, annotation).orNull() : null, null);
    }

    /**
     * Log a message at the warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void warning(@Nonnull CharSequence message, Element elem, Annotation annotation) {
        warning(message, elem,
                elem != null && annotation != null ? MoreElements.getAnnotationMirror(elem, annotation.getClass()).orNull() : null, null);
    }

    /**
     * Log a message at the warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void warning(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation) {
        warning(message, elem, annotation, null);
    }

    /**
     * Log a message at the warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     * @param value      annotation value this message is about
     */
    public void warning(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(Diagnostic.Kind.WARNING, requireNonNull(message, "message cannot be null"), elem, annotation, value);
    }

    //================================
    // mandatory warning level
    //================================

    /**
     * Log a message at the mandatory warning level
     *
     * @param message message text
     */
    public void mandatory(@Nonnull CharSequence message) {
        mandatory(message, null, null, null);
    }

    /**
     * Log a message at the mandatory warning level
     *
     * @param message message text
     * @param elem    element this message is about
     */
    public void mandatory(@Nonnull CharSequence message, Element elem) {
        mandatory(message, elem, null, null);
    }

    /**
     * Log a message at the mandatory warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation type (on the element) this message is about
     */
    public void mandatory(@Nonnull CharSequence message, Element elem, Class<? extends Annotation> annotation) {
        mandatory(message, elem,
                annotation != null && elem != null ? MoreElements.getAnnotationMirror(elem, annotation).orNull() : null, null);
    }

    /**
     * Log a message at the mandatory warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void mandatory(@Nonnull CharSequence message, Element elem, Annotation annotation) {
        mandatory(message, elem,
                elem != null && annotation != null ? MoreElements.getAnnotationMirror(elem, annotation.getClass()).orNull() : null, null);
    }

    /**
     * Log a message at the mandatory warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void mandatory(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation) {
        mandatory(message, elem, annotation, null);
    }

    /**
     * Log a message at the mandatory warning level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     * @param value      annotation value this message is about
     */
    public void mandatory(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, requireNonNull(message, "message cannot be null"), elem, annotation, value);
    }

    //================================
    // note level
    //================================

    /**
     * Log a message at the note level
     *
     * @param message message text
     */
    public void note(@Nonnull CharSequence message) {
        note(message, null, null, null);
    }

    /**
     * Log a message at the note level
     *
     * @param message message text
     * @param elem    element this message is about
     */
    public void note(@Nonnull CharSequence message, Element elem) {
        note(message, elem, null, null);
    }

    /**
     * Log a message at the note level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation type (on the element) this message is about
     */
    public void note(@Nonnull CharSequence message, Element elem, Class<? extends Annotation> annotation) {
        note(message, elem,
                annotation != null && elem != null ? MoreElements.getAnnotationMirror(elem, annotation).orNull() : null, null);
    }

    /**
     * Log a message at the note level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void note(@Nonnull CharSequence message, Element elem, Annotation annotation) {
        note(message, elem,
                elem != null && annotation != null ? MoreElements.getAnnotationMirror(elem, annotation.getClass()).orNull() : null, null);
    }

    /**
     * Log a message at the note level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void note(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation) {
        note(message, elem, annotation, null);
    }

    /**
     * Log a message at the note level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     * @param value      annotation value this message is about
     */
    public void note(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(Diagnostic.Kind.NOTE, requireNonNull(message, "message cannot be null"), elem, annotation, value);
    }

    //================================
    // other level
    //================================

    /**
     * Log a message at the other level
     *
     * @param message message text
     */
    public void other(@Nonnull CharSequence message) {
        other(message, null, null, null);
    }

    /**
     * Log a message at the other level
     *
     * @param message message text
     * @param elem    element this message is about
     */
    public void other(@Nonnull CharSequence message, Element elem) {
        other(message, elem, null, null);
    }

    /**
     * Log a message at the other level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation type (on the element) this message is about
     */
    public void other(@Nonnull CharSequence message, Element elem, Class<? extends Annotation> annotation) {
        other(message, elem,
                annotation != null && elem != null ? MoreElements.getAnnotationMirror(elem, annotation).orNull() : null, null);
    }

    /**
     * Log a message at the other level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void other(@Nonnull CharSequence message, Element elem, Annotation annotation) {
        other(message, elem,
                elem != null && annotation != null ? MoreElements.getAnnotationMirror(elem, annotation.getClass()).orNull() : null, null);
    }

    /**
     * Log a message at the other level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     */
    public void other(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation) {
        other(message, elem, annotation, null);
    }

    /**
     * Log a message at the other level
     *
     * @param message    message text
     * @param elem       element this message is about
     * @param annotation annotation (on the element) this message is about
     * @param value      annotation value this message is about
     */
    public void other(@Nonnull CharSequence message, Element elem, AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(Diagnostic.Kind.OTHER, requireNonNull(message, "message cannot be null"), elem, annotation, value);
    }


}
