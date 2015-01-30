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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractProcessorExtTest {

    @Mock
    private ProcessingEnvironment processingEnvironment;
    @Mock
    private Messager messager;
    @Mock
    private RoundEnvironment roundEnvironment;
    @Mock
    private Types types;
    @Mock
    private Elements elements;
    @Mock
    private Filer filer;

    private Fixture fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new Fixture();

        when(processingEnvironment.getMessager())
                .thenReturn(messager);
        when(processingEnvironment.getTypeUtils())
                .thenReturn(types);
        when(processingEnvironment.getElementUtils())
                .thenReturn(elements);
        when(processingEnvironment.getFiler())
                .thenReturn(filer);

        fixture.init(processingEnvironment);
    }

    @Test
    public void testProcess() throws Exception {
        Set<? extends TypeElement> set = new HashSet<>();
        boolean result = fixture.process(set, roundEnvironment);
        assertFalse(result);
        assertSame(set, fixture.annotations);
        assertSame(roundEnvironment, fixture.roundEnv);

        verify(processingEnvironment).getMessager();
        verifyNoMoreInteractions(processingEnvironment, messager, roundEnvironment);
    }

    @Test
    public void testProcess_abort() throws Exception {
        Set<? extends TypeElement> set = new HashSet<>();

        fixture.abort = true;
        fixture.result = true;

        // We're throwing an exception, but it shouldn't propagate up to here.
        // We're also telling it to return true, but the superclass should return false
        boolean result = fixture.process(set, roundEnvironment);
        assertFalse(result);

        verify(processingEnvironment, times(2)).getMessager();
        verify(messager).printMessage(Diagnostic.Kind.ERROR, "message", null, null, null);
        verifyNoMoreInteractions(processingEnvironment, messager, roundEnvironment);
    }

    @Test
    public void testLogger() throws Exception {
        Logger logger = fixture.logger();
        assertNotNull(logger);
        assertSame(messager, ReflectionTestUtils.getField(logger, "messager"));
        assertSame(logger, fixture.logger());//Make sure it always returns the same one
    }

    @Test
    public void testUtilityGetters() {
        assertSame(types, fixture.types());
        assertSame(elements, fixture.elements());
        assertSame(messager, fixture.messager());
        assertSame(filer, fixture.filer());
    }


    private static final class Fixture extends AbstractProcessorExt {

        private boolean abort;

        private boolean result;

        public Set<? extends TypeElement> annotations;
        public RoundEnvironment roundEnv;

        public void reset() {
            annotations = null;
            roundEnv = null;
            abort = false;
            result = false;
        }

        @Override
        protected boolean processAbortable(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws AbortProcessingException {
            if (this.annotations != null || this.roundEnv != null) {
                throw new IllegalStateException("Did not reset fixture between invocations");
            }
            if (abort) {
                throw new AbortProcessingException("message", null, null, null);
            }
            this.annotations = annotations;
            this.roundEnv = roundEnv;
            return result;
        }
    }
}