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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class LoggerTest {

    @Mock
    private Messager messager;

    @Mock
    private Element element;

    @Mock
    private AnnotationMirror annotationMirror;

    @Mock
    private AnnotationValue annotationValue;

    @Mock
    private Annotation annotation;

    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = new Logger(messager);
    }

    //=========================
    // fatal level
    //=========================

    @Test
    public void testFatal_all() throws Exception {
        try {
            logger.fatal("test", element, annotationMirror, annotationValue);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(element, ex.getElement());
            assertEquals(annotationMirror, ex.getAnnotation());
            assertEquals(annotationValue, ex.getValue());
        }

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, annotationValue);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testFatal_noValue() throws Exception {
        try {
            logger.fatal("test", element, annotationMirror);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(element, ex.getElement());
            assertEquals(annotationMirror, ex.getAnnotation());
            assertNull(ex.getValue());
        }

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testFatal_annotation() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        MyOverride annotation = new MyOverride();

        try {
            logger.fatal("test", element, annotation);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(element, ex.getElement());
            assertEquals(annotationMirror, ex.getAnnotation());
            assertNull(ex.getValue());
        }

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, null);
    }

    @Test
    public void testFatal_annotationClass() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        try {
            logger.fatal("test", element, Override.class);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(element, ex.getElement());
            assertEquals(annotationMirror, ex.getAnnotation());
            assertNull(ex.getValue());
        }

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, null);
    }

    @Test
    public void testFatal_noAnnotation() throws Exception {
        try {
            logger.fatal("test", element);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertEquals(element, ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testFatal_justMessage() throws Exception {
        try {
            logger.fatal("test");
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertNull(ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", null, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testFatal_nullable() throws Exception {
        try {
            logger.fatal("test", null);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertNull(ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }
        try {
            logger.fatal("test", null, (Class<? extends Annotation>) null);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertNull(ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }
        try {
            logger.fatal("test", null, (Annotation) null);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertNull(ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }
        try {
            logger.fatal("test", null, (AnnotationMirror) null);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertNull(ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }
        try {
            logger.fatal("test", null, null, null);
            fail("Should have thrown exception");
        } catch (AbstractProcessorExt.AbortProcessingException ex) {
            assertEquals("test", ex.getMessage());
            assertNull(ex.getElement());
            assertNull(ex.getAnnotation());
            assertNull(ex.getValue());
        }

        try {
            logger.fatal(null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.fatal(null, null, (Class<? extends Annotation>) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.fatal(null, null, (Annotation) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.fatal(null, null, (AnnotationMirror) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.fatal(null, null, null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }
    }

    //=========================
    // error level
    //=========================

    @Test
    public void testError_all() throws Exception {
        logger.error("test", element, annotationMirror, annotationValue);

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, annotationValue);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testError_noValue() throws Exception {
        logger.error("test", element, annotationMirror);

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testError_annotation() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        MyOverride annotation = new MyOverride();

        logger.error("test", element, annotation);

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, null);
    }

    @Test
    public void testError_annotationClass() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        logger.error("test", element, Override.class);

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, annotationMirror, null);
    }

    @Test
    public void testError_noAnnotation() throws Exception {
        logger.error("test", element);

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", element, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testError_justMessage() throws Exception {
        logger.error("test");

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "test", null, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testError_nullable() throws Exception {
        logger.error("test", null);
        logger.error("test", null, (Class<? extends Annotation>) null);
        logger.error("test", null, (Annotation) null);
        logger.error("test", null, (AnnotationMirror) null);
        logger.error("test", null, null, null);

        try {
            logger.error(null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.error(null, null, (Class<? extends Annotation>) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.error(null, null, (Annotation) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.error(null, null, (AnnotationMirror) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.error(null, null, null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }
    }

    //=========================
    // warning level
    //=========================

    @Test
    public void testWarning_all() throws Exception {
        logger.warning("test", element, annotationMirror, annotationValue);

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "test", element, annotationMirror, annotationValue);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testWarning_noValue() throws Exception {
        logger.warning("test", element, annotationMirror);

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "test", element, annotationMirror, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testWarning_annotation() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        MyOverride annotation = new MyOverride();

        logger.warning("test", element, annotation);

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "test", element, annotationMirror, null);
    }

    @Test
    public void testWarning_annotationClass() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        logger.warning("test", element, Override.class);

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "test", element, annotationMirror, null);
    }

    @Test
    public void testWarning_noAnnotation() throws Exception {
        logger.warning("test", element);

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "test", element, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testWarning_justMessage() throws Exception {
        logger.warning("test");

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "test", null, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testWarning_nullable() throws Exception {
        logger.warning("test", null);
        logger.warning("test", null, (Class<? extends Annotation>) null);
        logger.warning("test", null, (Annotation) null);
        logger.warning("test", null, (AnnotationMirror) null);
        logger.warning("test", null, null, null);

        try {
            logger.warning(null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.warning(null, null, (Class<? extends Annotation>) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.warning(null, null, (Annotation) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.warning(null, null, (AnnotationMirror) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.warning(null, null, null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }
    }


    //=========================
    // mandatory level
    //=========================

    @Test
    public void testMandatory_all() throws Exception {
        logger.mandatory("test", element, annotationMirror, annotationValue);

        verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, "test", element, annotationMirror, annotationValue);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testMandatory_noValue() throws Exception {
        logger.mandatory("test", element, annotationMirror);

        verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, "test", element, annotationMirror, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testMandatory_annotation() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        MyOverride annotation = new MyOverride();

        logger.mandatory("test", element, annotation);

        verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, "test", element, annotationMirror, null);
    }

    @Test
    public void testMandatory_annotationClass() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        logger.mandatory("test", element, Override.class);

        verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, "test", element, annotationMirror, null);
    }

    @Test
    public void testMandatory_noAnnotation() throws Exception {
        logger.mandatory("test", element);

        verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, "test", element, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testMandatory_justMessage() throws Exception {
        logger.mandatory("test");

        verify(messager).printMessage(Diagnostic.Kind.MANDATORY_WARNING, "test", null, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testMandatory_nullable() throws Exception {
        logger.mandatory("test", null);
        logger.mandatory("test", null, (Class<? extends Annotation>) null);
        logger.mandatory("test", null, (Annotation) null);
        logger.mandatory("test", null, (AnnotationMirror) null);
        logger.mandatory("test", null, null, null);

        try {
            logger.mandatory(null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.mandatory(null, null, (Class<? extends Annotation>) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.mandatory(null, null, (Annotation) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.mandatory(null, null, (AnnotationMirror) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.mandatory(null, null, null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }
    }


    //=========================
    // note level
    //=========================

    @Test
    public void testNote_all() throws Exception {
        logger.note("test", element, annotationMirror, annotationValue);

        verify(messager).printMessage(Diagnostic.Kind.NOTE, "test", element, annotationMirror, annotationValue);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testNote_noValue() throws Exception {
        logger.note("test", element, annotationMirror);

        verify(messager).printMessage(Diagnostic.Kind.NOTE, "test", element, annotationMirror, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testNote_annotation() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        MyOverride annotation = new MyOverride();

        logger.note("test", element, annotation);

        verify(messager).printMessage(Diagnostic.Kind.NOTE, "test", element, annotationMirror, null);
    }

    @Test
    public void testNote_annotationClass() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        logger.note("test", element, Override.class);

        verify(messager).printMessage(Diagnostic.Kind.NOTE, "test", element, annotationMirror, null);
    }

    @Test
    public void testNote_noAnnotation() throws Exception {
        logger.note("test", element);

        verify(messager).printMessage(Diagnostic.Kind.NOTE, "test", element, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testNote_justMessage() throws Exception {
        logger.note("test");

        verify(messager).printMessage(Diagnostic.Kind.NOTE, "test", null, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testNote_nullable() throws Exception {
        logger.note("test", null);
        logger.note("test", null, (Class<? extends Annotation>) null);
        logger.note("test", null, (Annotation) null);
        logger.note("test", null, (AnnotationMirror) null);
        logger.note("test", null, null, null);

        try {
            logger.note(null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.note(null, null, (Class<? extends Annotation>) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.note(null, null, (Annotation) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.note(null, null, (AnnotationMirror) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.note(null, null, null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }
    }


    //=========================
    // other level
    //=========================

    @Test
    public void testOther_all() throws Exception {
        logger.other("test", element, annotationMirror, annotationValue);

        verify(messager).printMessage(Diagnostic.Kind.OTHER, "test", element, annotationMirror, annotationValue);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testOther_noValue() throws Exception {
        logger.other("test", element, annotationMirror);

        verify(messager).printMessage(Diagnostic.Kind.OTHER, "test", element, annotationMirror, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testOther_annotation() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        MyOverride annotation = new MyOverride();

        logger.other("test", element, annotation);

        verify(messager).printMessage(Diagnostic.Kind.OTHER, "test", element, annotationMirror, null);
    }

    @Test
    public void testOther_annotationClass() throws Exception {
        TypeElement typeElement = mock(TypeElement.class);
        DeclaredType annotType = mock(DeclaredType.class);
        Name name = mock(Name.class);
        when(element.getAnnotationMirrors())
                .thenAnswer(new AnnotationListAnswer());
        when(annotationMirror.getAnnotationType())
                .thenReturn(annotType);
        when(annotType.asElement())
                .thenReturn(typeElement);
        when(typeElement.getQualifiedName())
                .thenReturn(name);
        when(name.contentEquals(anyString()))
                .thenReturn(true);
        //noinspection unchecked
        when(typeElement.accept((ElementVisitor<TypeElement, Void>) Mockito.notNull(), any(Void.class)))
                .thenReturn(typeElement);

        logger.other("test", element, Override.class);

        verify(messager).printMessage(Diagnostic.Kind.OTHER, "test", element, annotationMirror, null);
    }

    @Test
    public void testOther_noAnnotation() throws Exception {
        logger.other("test", element);

        verify(messager).printMessage(Diagnostic.Kind.OTHER, "test", element, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @Test
    public void testOther_justMessage() throws Exception {
        logger.other("test");

        verify(messager).printMessage(Diagnostic.Kind.OTHER, "test", null, null, null);
        verifyNoMoreInteractions(messager, element, annotationMirror, annotationValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testOther_nullable() throws Exception {
        logger.other("test", null);
        logger.other("test", null, (Class<? extends Annotation>) null);
        logger.other("test", null, (Annotation) null);
        logger.other("test", null, (AnnotationMirror) null);
        logger.other("test", null, null, null);

        try {
            logger.other(null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.other(null, null, (Class<? extends Annotation>) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.other(null, null, (Annotation) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.other(null, null, (AnnotationMirror) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }

        try {
            logger.other(null, null, null, null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException ex) {
            assertEquals("message cannot be null", ex.getMessage());
        }
    }


    private static final class MyOverride implements Override {
        @Override
        public Class<? extends Annotation> annotationType() {
            return Override.class;
        }
    }

    private class AnnotationListAnswer implements Answer<List<AnnotationMirror>> {
        @Override
        public List<AnnotationMirror> answer(InvocationOnMock invocation) throws Throwable {
            return Arrays.asList(annotationMirror);
        }
    }
}