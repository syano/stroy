package org.openCage.stroy.file;

import com.google.inject.Inject;
import org.openCage.kleinod.observe.VoidListeners;
import org.openCage.ruleofthree.ThreeKey;
import org.openCage.ruleofthree.property.HashMapProperty;
import org.openCage.ruleofthree.property.MapProperty;
import org.openCage.util.external.DesktopXs;
import org.openCage.util.external.ExternalProgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***** BEGIN LICENSE BLOCK *****
 * BSD License (2 clause)
 * Copyright (c) 2006 - 2012, Stephan Pfab
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Stephan Pfab BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***** END LICENSE BLOCK *****/

public class FileTypes {


    private final MapProperty<Action> fileTypes;
    private VoidListeners observers = new VoidListeners();

    @Inject
    public FileTypes(MapProperty<Action> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public String getOpen(String extension) {
        return getOrCreate(extension).getOpen();
    }

    private Action getOrCreate( String ext ) {
        return getOrCreate( ThreeKey.valueOf(ext));
    }

    private Action getOrCreate( ThreeKey ext ) {
        if ( !fileTypes.containsKey(ext)) {
            fileTypes.put( ext, new Action( "unknown file type" ));
        }

        return fileTypes.get(ext);
    }


    public boolean hasOpen(String extension) {
        return getOrCreate(extension).hasOpen( extension );
    }

    public boolean hasDiffType(String extension) {
        return getOrCreate(extension).hasDiff(extension);
    }

    public String getDiffType(String extension) {
        return getOrCreate(extension).getDiff();
    }

    public Collection<String> getTypeList() {
        List<String>  ret = new ArrayList<String>();
        for( ThreeKey key : fileTypes.keySet() ) {
            ret.add(key.toString());
        }
        return ret;
    }

    public String getDescription(String ext) {
        return getOrCreate(ext).getDescription();
    }

    public SimilarityAlgorithm getAlgo(String ext) {
        return getOrCreate(ext).getAlgo();
    }

    public void setDescription(String ext, String text) {
        getOrCreate(ext).setDescription(text);
    }

    public void setAlgo(String ext, SimilarityAlgorithm algo) {
        getOrCreate(ext).setAlgo( algo );
    }

    public void setAlgo(String ext, String algo) {
        setAlgo( ext,SimilarityAlgorithm.valueOf(algo));
    }

    public void setOpenProg(String ext, String text) {
        getOrCreate(ext).setOpen( text );
    }

    public void setDiffProg(String ext, String text) {
        getOrCreate(ext).setDiff(text);
    }


    public static HashMapProperty<Action> getInitialMap() {

        HashMapProperty<Action> fileTypes = new HashMapProperty<Action>();

        // java
        fileTypes.put( ThreeKey.valueOf("java"),
                new Action(
                        "java source",
                        SimilarityAlgorithm.java,
                        ExternalProgs.STANDARD_DIFF,
                        DesktopXs.STANDARD_OPEN,
                        true ));

        // json
        fileTypes.put( ThreeKey.valueOf("json"),
                new Action(
                        "json",
                        SimilarityAlgorithm.text,
                        ExternalProgs.STANDARD_DIFF,
                        DesktopXs.STANDARD_OPEN,
                        true,
                        false,
                        true ));


        // c
        final String [] cc = {
                "cpp", "C++ source",
                "cc",  "C++ source",
                "h",   "C/C++ header",
                "c",   "C source" };
        add( fileTypes, SimilarityAlgorithm.c, ExternalProgs.STANDARD_DIFF, DesktopXs.STANDARD_OPEN, cc, true );

        // text
        final String[] text = {
                "text", "text",
                "txt", "text",
                "tex", "TeX document source",
                "log", "log file",
                "from", "",
                "jdl", "java data language",
                "jsp", "java server page source",
                "sh", "UNIX shell script",
                "bash", "UNIX shell script, bash dialect",
                "bashrc", "bash script run one opening a shell ",
                "php", "php source",
                "ruby", "ruby source",
                "doc", "word document",
                "properties", "java properties",
                "vcproj", "MS visual studio project description (XML)",
                "mf", "java Manifest",
                "bat", "MSDOS batch script",
                "py", "python source",
                "ini", "MSDOS properties, key=val", // key=val list
                "sln",  "MS Visual Studio solution (XML?)",
                "policy", "java plugin ??",   // java plugin ??
                "css", "Cascading Style Sheet",
                "js", "Java Script",
                "m", "Objective c",
                "pch", "objective c || precompiled header ??"
        };
        add(fileTypes, SimilarityAlgorithm.text, ExternalProgs.STANDARD_DIFF, DesktopXs.STANDARD_OPEN, text, true );

        final String[] xml = {
                "xml", "xml",
                "html", "hypertext meta language",
                "htm", "hypertext meta language",
                "ism", "",
                "dtd", "XML scheme definition",
                "jspx", "java server page in xml",
                "plist", "",
                "xls", "MS Excel data",
                "iml", "IntelliJ Idea module description",
                "ipr", "IntelliJ Idea project description",
                "iws", "" };
        addXML(fileTypes, SimilarityAlgorithm.xml, ExternalProgs.STANDARD_DIFF, DesktopXs.STANDARD_OPEN, xml );

        final String[] binary = {
                "jnilib", "odx version of java jni",
                "dvi", "compiled tex document",
                "dylib", "",
                "so", "UNIX library",
                "exe", "MS Windows executable",
                "dll", "MS dynamic link library",
                "lib", "",
                "vsd", "",
                "jar", "java library file",
                "war", "java library for web",
                "tgz", "archive, tarred and gzipped",
                "tar", "archive, tar format",
                "gz", "gzipped file",
                "zip", "archive, zip format",
                "jdb", "",
                "pbproj", "", // ?? osx

                "jpg", "picture jpeg format",
                "jpeg", "picture jpeg format",
                "gif", "picture, gif format",
                "png", "picture, png format",
                "bmp", "picture, bitmap format",
                "raw", "picture, raw format (different per camera)",
                "icns", "picture, osx icon format",

                "mp3", "audio, mpeg3 format",
                "wav", "audio, wav format",

                "jude", "jude (UML tool) file",
                "pdf", "Portable Document Format (Adobe)",
                "dat", "", // ???
                "ncb", "",
                "suo", "MS Visual Studio something",   // for ms visual studio
                "pbxtree", "",
                "header", "",
                "pbxsymbols", "",
                "xcodeproj", "OSX xcode project",
                "xcode", "",
                "pbxproj", "",
        };
        add(fileTypes, SimilarityAlgorithm.none, ExternalProgs.unknown, DesktopXs.STANDARD_OPEN, binary, false );

        final String[] bundles = {
                "pages", "pages document",
                "key", "keynote presentation",
                "numbers", "numbers spreadsheet",
                "app", "osx application"};
        add(fileTypes, SimilarityAlgorithm.none, ExternalProgs.unknown, DesktopXs.STANDARD_OPEN, bundles, false );



        fileTypes.put(ThreeKey.valueOf("svn"), new Action( "subversion local information"));
        fileTypes.put(ThreeKey.valueOf("DS_Store"), new Action( "OSX finder files"));
        fileTypes.put(ThreeKey.valueOf("class"), new Action(
                "java compiled class file",
                SimilarityAlgorithm.none,
                ExternalProgs.unknown,
                ExternalProgs.unknown, false ));
        fileTypes.put(ThreeKey.valueOf("obj"), new Action(
                "MS compile c file",
                SimilarityAlgorithm.none,
                ExternalProgs.unknown,
                ExternalProgs.unknown, false ));
        fileTypes.put(ThreeKey.valueOf("o"), new Action(
                "UNIX compiled",
                SimilarityAlgorithm.none,
                ExternalProgs.unknown,
                ExternalProgs.unknown, false ));
        fileTypes.put(ThreeKey.valueOf("copyarea.db"), new Action(
                "ClearCase file",
                SimilarityAlgorithm.none,
                ExternalProgs.unknown,
                ExternalProgs.unknown, false ));
        fileTypes.put(ThreeKey.valueOf("copyarea.dat"), new Action(
//                true,
                "ClearCase helper file",
                SimilarityAlgorithm.none,
                ExternalProgs.unknown,
                ExternalProgs.unknown, false ));
        fileTypes.put(ThreeKey.valueOf("get_date.dat"), new Action(
                //              true,
                "Alienbrain local info file",
                SimilarityAlgorithm.none,
                ExternalProgs.unknown,
                ExternalProgs.unknown, false ));

        return fileTypes;
    }

     private static void add(MapProperty<Action> fileTypes, final SimilarityAlgorithm algo, String diff, String open, String[] exts, boolean isText) {
        for ( int idx = 0; idx < exts.length; idx += 2 ) {
            fileTypes.put(ThreeKey.valueOf(exts[idx]), new Action(exts[idx + 1], algo, diff, open, isText ));
        }
    }

    private static void addXML(MapProperty<Action> fileTypes, final SimilarityAlgorithm algo, String diff, String open, String[] exts ) {
        for ( int idx = 0; idx < exts.length; idx += 2 ) {
            fileTypes.put(ThreeKey.valueOf(exts[idx]), new Action(exts[idx + 1], algo, diff, open, true, true, false ));
        }
    }

    public Action getAction(String ext) {
        return fileTypes.get( ThreeKey.valueOf(ext ));
    }

    // TODO
    public boolean isText(String type) {
        switch ( getAlgo( type ) ) {
            case c:
            case java:
            case text:
            case xml:
                return false;
            default:
                return false;
        }
    }
}
