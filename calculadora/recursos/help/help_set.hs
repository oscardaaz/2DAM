<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
        "http://java.sun.com/products/javahelp/helpset_2_0.dtd">
<helpset version="2.0">
    <title>Ayuda - Calculadora</title>

    <!-- Mapa de IDs -> HTML -->
    <maps>
        <homeID>bienvenida</homeID>
        <mapref location="map_file.jhm"/>
    </maps>

    <!-- Vista: Contenido (TOC) -->
    <view>
        <name>TOC</name>
        <label>Contenido</label>
        <type>javax.help.TOCView</type>
        <data>toc.xml</data>
    </view>

    <!-- Vista: Índice -->
    <view>
        <name>Index</name>
        <label>Índice</label>
        <type>javax.help.IndexView</type>
        <data>indice.xml</data>
    </view>

    <!-- Vista: Búsqueda -->
    <view>
        <name>Search</name>
        <label>Búsqueda</label>
        <type>javax.help.SearchView</type>
        <data engine="com.sun.java.help.search.DefaultSearchEngine">
            JavaHelpSearch
        </data>
    </view>
</helpset>
