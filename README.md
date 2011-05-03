UniTube - Media sharing platform for universities
=================================================

UniTube is a media sharing platform designed for use in tertiary education
institutions. It has been developed by Educational Media, Higher Education
Development Centre, University of Otago, Dunedin, NZ.

You can share video, audio, Microsoft office, OpenOffice, PDF and many more
file types with UniTube.

Features
--------

    * Supports many file types
    * media files can be embedded in any html page

System requirements
-------------------

    * Modern Operating Systems, such as Windows XP, Windows Vista, Windows
      2003 Server, Linux (Ubuntu). Although UniTube has no problem to run
      under Windows, it is highly recommended to use a Linux server,
      especially a Ubuntu server. All our testings are carried under Ubuntu.
    * Java JDK 5.0 or JDK 6.0
    * Java application server (sucn as Tomcat 5.5, Tomcat 6)
    * FFmpeg
    * OpenOffice
    * SWFTools
    * ImageMagick

Installation
------------

After download all source code, run:

    * mvn jetty:run

To access UniTube, just point your browser to <http://localhost:8080/unitube/>.
If you can access UniTube without any errors, congratulations! You have
installed the UniTube web application successfully.

UniTube utilises many external open source and free software tools. To be able
to use all the functions of UniTube, you have to install other software as well,
including FFmpeg, OpenOffice, SWFTools, ImageMagick.

Configuration
-------------

The main config file is src/main/resources/config.properties.

The config file for log is src/main/resources/log4j.properties.

Links
-----

    * JDK <http://java.sun.com/javase/downloads/index.jsp>
    * Tomcat <http://tomcat.apache.org/>
    * FFmpeg <http://ffmpeg.mplayerhq.hu>
    * OpenOffice <http://www.openoffice.org>
    * SWFTools <http://www.swftools.org>
    * ImageMagick <http://www.imagemagick.org>

License
-------

Copyright (C) 2011 University of Otago <http://www.otago.ac.nz>.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.