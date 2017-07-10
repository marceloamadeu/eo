/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 eolang.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.compiler.syntax;

import java.util.Arrays;
import org.cactoos.InputHasContent;
import org.cactoos.list.ArrayAsIterable;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Tree}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class TreeTest {

    /**
     * Tree can generate Java files.
     *
     * @throws Exception If some problem inside
     */
    @Test
    public void generatesJavaFiles() throws Exception {
        MatcherAssert.assertThat(
            new Tree(
                Arrays.asList(
                    new Type(
                        "Car",
                        Arrays.asList(
                            new Method(
                                "drive",
                                Arrays.asList(
                                    new Parameter("x", "Integer"),
                                    new Parameter("y", "Long")
                                ),
                                "Int"
                            )
                        )
                    )
                )
            ).java(),
            Matchers.hasEntry(
                Matchers.equalTo("Car.java"),
                new InputHasContent(
                    Matchers.stringContainsInOrder(
                        new ArrayAsIterable<>(
                            "package eo;",
                            "public interface Car {",
                            "Int drive(final Integer x, final Long y);",
                            "}"
                        )
                    )
                )
            )
        );
    }
}
