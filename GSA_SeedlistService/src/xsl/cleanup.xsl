<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:wplc="http://www.ibm.com/wplc/atom/1.0" exclude-result-prefixes="#all">
<xsl:output method="xml" indent="yes" doctype-public="-//Google//DTD GSA Feeds//EN" doctype-system="http://google.aurora.org:7800/gsafeed.dtd"/>
  <xsl:template match="/">
		<gsafeed>
		  <header>
			  <datasource>caregiver_wcm</datasource>
			  <feedtype>metadata-and-url</feedtype>
		  </header>
			<group action="delete">
				<xsl:apply-templates select="atom:feed/atom:entry" />
			</group>
      </gsafeed>
  </xsl:template>

  <xsl:template match="atom:entry">
	  <record action="delete" mimetype="text/html">
		  <xsl:attribute name="displayurl">
			  <xsl:for-each select="atom:link">http://portal-devcg.aurora.org<xsl:value-of select="@href"/></xsl:for-each>
		  </xsl:attribute>
		  <xsl:attribute name="url">
			  <xsl:for-each select="atom:content">http://portal-devcg.aurora.org<xsl:value-of select="@src"/></xsl:for-each>
		  </xsl:attribute>	  
		</record>
  </xsl:template>
</xsl:stylesheet>