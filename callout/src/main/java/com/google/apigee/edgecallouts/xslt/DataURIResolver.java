// Copyright 2015-2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package com.google.apigee.edgecallouts.xslt;

import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.io.IOException;

public class DataURIResolver implements URIResolver {
    private URIResolver _orig;
    public DataURIResolver(URIResolver orig) {
        _orig = orig;
    }

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (base.equals("") && href.startsWith("data:text/xml,")) {
            // immediate XML string
            try {
                String xmlString = href.substring(14);
                InputStream in = IOUtils.toInputStream(xmlString, "UTF-8");
                return new StreamSource(in);
            }
            catch (IOException ioexc1) {
                throw new TransformerException("while converting", ioexc1);
            }
        }

        return _orig.resolve(href,base);
    }
}
