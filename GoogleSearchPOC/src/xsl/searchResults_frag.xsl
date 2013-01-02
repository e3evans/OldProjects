<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  version="2.0">
	<xsl:output omit-xml-declaration="yes"/>
	<xsl:template match="/">
		<xsl:call-template name="results"/>
	</xsl:template>
	
	<xsl:template name="results">
		<ul class="acgc_search_results_list">
				
			<xsl:for-each select="/GSP/RES/R">
				<xsl:choose>
				<xsl:when test="position() mod 2=1">
					<li class="even">
										<strong>
							<a>
							<xsl:attribute name="href"><xsl:value-of select="U"/></xsl:attribute>
							<xsl:attribute name="title"><xsl:value-of select="T" disable-output-escaping="yes"/></xsl:attribute>
							<xsl:value-of select="T" disable-output-escaping="yes"/>
							</a>
						</strong>
						<p>
							<xsl:value-of select="S" disable-output-escaping="yes"/>
						</p>
						<a>
							<xsl:attribute name="href"><xsl:value-of select="U"/></xsl:attribute>
							<xsl:attribute name="title">Visit <xsl:value-of select="U"/>
							</xsl:attribute>
							<xsl:attribute name="target">_blank</xsl:attribute>
							<xsl:value-of select="U"/>
						</a>
					</li>
				</xsl:when>
				<xsl:otherwise>
					<li class="odd">
										<strong>
							<a>
							<xsl:attribute name="href"><xsl:value-of select="U"/></xsl:attribute>
							<xsl:attribute name="title"><xsl:value-of select="T" disable-output-escaping="yes"/></xsl:attribute>
							<xsl:value-of select="T" disable-output-escaping="yes"/>
							</a>
						</strong>
						<p>
							<xsl:value-of select="S" disable-output-escaping="yes"/>
						</p>
						<a>
							<xsl:attribute name="href"><xsl:value-of select="U"/></xsl:attribute>
							<xsl:attribute name="title">Visit <xsl:value-of select="U"/>
							</xsl:attribute>
							<xsl:attribute name="target">_blank</xsl:attribute>
							<xsl:value-of select="U"/>
						</a>
					</li>
				</xsl:otherwise>
				</xsl:choose>
	
			</xsl:for-each>
		
		</ul>
	
	</xsl:template>
</xsl:stylesheet>