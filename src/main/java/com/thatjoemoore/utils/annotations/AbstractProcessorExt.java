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

import javax.annotation.Nonnull;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by adm.jmooreoa on 12/30/14.
 */
public abstract class AbstractProcessorExt extends AbstractProcessor {

    @Override
    public synchronized void init(@Nonnull ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        log = new Logger(processingEnv.getMessager());
    }

    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            return processAbortable(annotations, roundEnv);
        } catch (AbortProcessingException ex) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), ex.element, ex.annotation, ex.value);
            return false;
        }
    }

    protected abstract boolean processAbortable(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws AbortProcessingException;

    private Logger log;

    protected final Logger logger() {
        return log;
    }

    protected final Types types() {
        return processingEnv.getTypeUtils();
    }

    protected final Elements elements() {
        return processingEnv.getElementUtils();
    }

    protected final Messager messager() {
        return processingEnv.getMessager();
    }

    protected final Filer filer() {
        return processingEnv.getFiler();
    }

    public static class AbortProcessingException extends RuntimeException {
        private final Element element;
        private final AnnotationMirror annotation;
        private final AnnotationValue value;

        public AbortProcessingException(String message, Element element, AnnotationMirror annotation, AnnotationValue value) {
            super(message);
            this.element = element;
            this.annotation = annotation;
            this.value = value;
        }

        public Element getElement() {
            return element;
        }

        public AnnotationMirror getAnnotation() {
            return annotation;
        }

        public AnnotationValue getValue() {
            return value;
        }
    }

}
