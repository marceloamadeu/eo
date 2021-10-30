/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2021 Yegor Bugayenko
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
package EOorg.EOeolang;

import org.eolang.AtBound;
import org.eolang.AtFree;
import org.eolang.AtLambda;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhEta;
import org.eolang.PhMethod;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link EObool}.
 *
 * [] > parent
 *   memory TRUE > toggle
 *   toggle.while > @
 *     [x] > kid
 *       ^.^.write FALSE
 *
 * @since 0.1
 */
public final class EOboolEOwhileTest {

    @Test
    public void loopsOverAbstractObjects() {
        final Phi toggle = new PhMethod(new Parent(new PhEta()), "toggle");
        new Dataized(
            new PhWith(
                new PhMethod(toggle, "write"),
                0, new Data.ToPhi(true)
            )
        ).take();
        new Dataized(
            new PhWith(
                new PhMethod(toggle, "while"),
                0, new EOboolEOwhileTest.Kid(new PhEta())
            )
        ).take();
    }

    public static class Parent extends PhDefault {
        public Parent(final Phi parent) {
            super(parent);
            this.add("toggle", new AtBound(new AtLambda(
                this, self -> new EOmemory(self)
            )));
        }
    }

    public static class Kid extends PhDefault {
        public Kid(final Phi parent) {
            super(parent);
            this.add("x", new AtFree());
            this.add("φ", new AtBound(new AtLambda(
                this, self -> {
                    new Dataized(
                        new PhWith(
                            self.attr("ρ").get()
                                .attr("ρ").get()
                                .attr("write").get(),
                            0, new Data.ToPhi(false)
                        )
                    ).take();
                    return new Data.ToPhi(1L);
                }
            )));
        }
    }
}