/*
 * Copyright 2015 Joseph Moore
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
import com.google.common.base.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by adm.jmooreoa on 1/1/15.
 */
@ParametersAreNonnullByDefault
public final class ElementsExt {
    private ElementsExt() {}

    public static PackageElement getFirstAnnotatedPackage(TypeElement element, Class<? extends Annotation> annotationClass, Elements elements) {
        for (PackageElement each : getPackages(element, elements)) {
            Optional<AnnotationMirror> mirror = MoreElements.getAnnotationMirror(each, annotationClass);
            if (mirror.isPresent()) {
                return each;
            }
        }
        return null;
    }

    public static List<PackageElement> getPackages(Element element, Elements elements) {
        List<PackageElement> packs = new ArrayList<>();
        PackageElement pack = MoreElements.getPackage(element);
        while (pack != null && !pack.isUnnamed()) {
            packs.add(pack);

            CharSequence qual = pack.getQualifiedName();
            int end = qual.length() - pack.getSimpleName().length() - 1;
            if (end <= 0) {
                return packs;
            }

            CharSequence name = qual.subSequence(0, end);
            pack = elements.getPackageElement(name);
        }
        return packs;
    }

    public static AnnotationValue annotationValue(@Nullable AnnotationMirror mirror, String name, Elements elements) {
        if (mirror == null) {
            return null;
        }
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = elements.getElementValuesWithDefaults(mirror);
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> each : map.entrySet()) {
            if (each.getKey().getSimpleName().toString().equals(name)) {
                return each.getValue();
            }
        }
        return null;
    }


}
