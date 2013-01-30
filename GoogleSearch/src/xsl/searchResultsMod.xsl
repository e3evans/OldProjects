<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  version="2.0">
<xsl:output omit-xml-declaration="yes"/>
<xsl:param name="contextPath"/>
<xsl:param name="search_env"/>
<xsl:variable name="collection" select="/GSP/PARAM[@name='site']/@original_value"/>
<xsl:template match="/">
	<xsl:choose>
		<xsl:when test="/GSP/RES">
			<script>
				var SN = <xsl:value-of select="/GSP/RES/@SN"/>;
				var PP = <xsl:value-of select="/GSP/PARAM[@name='num']/@original_value"/>;
				var Q = '<xsl:value-of select="/GSP/PARAM[@name='q']/@original_value"/>';
				var page_query = '<xsl:value-of select="/GSP/PARAM[@name='q']/@original_value"/>';
				var page_start = '<xsl:value-of select="/GSP/PARAM[@name='page']/@original_value"/>';
				var page_site = '<xsl:value-of select="/GSP/PARAM[@name='site']/@original_value"/>';
			</script>
			
			<div class="acgc_top_content_wrap">
				<div class="acgc_top_content_box acgc_relative">
				<h1><span class="acgc_top_content_small_txt">Showing </span><xsl:value-of select="/GSP/RES/M"/><span class="acgc_top_content_small_txt"> search results for </span>&quot;<xsl:value-of select="translate(/GSP/PARAM[@name='q']/@original_value,'+',' ')"/>&quot;</h1>
				</div>
			</div>
				<xsl:call-template name="resultsBar"/>
				<xsl:call-template name="searchResults"/>
				<script>
					<xsl:attribute name="src"><xsl:value-of select="$contextPath"/>/js/clicklog.js</xsl:attribute>
					<xsl:attribute name="language">JavaScript</xsl:attribute>
					&#160;
				</script>
		</xsl:when>
		<xsl:otherwise>
		
		<div class="acgc_top_content_wrap">
	<div class="acgc_top_content_box acgc_relative">
		<h1><span class="acgc_top_content_small_txt">No results </span><xsl:value-of select="/GSP/RES/M"/><span class="acgc_top_content_small_txt">found for </span>&quot;<xsl:value-of select="translate(/GSP/PARAM[@name='q']/@original_value,'+',' ')"/>&quot;</h1>
	</div>
</div>
<div class="acgc_relative" id="acgc_recordsorter">&#160;</div>
	<div class="acgc_content_box">
		<div class="acgc_spacer_10 acgc_bg_white">&#160;</div>
		<div class="acgc_content_box_body acgc_relative">
			<div class="acgc_spacer_10 acgc_bg_white">&#160;</div>
			<div class="acgc_no_search_results">
				<p>Your search - <strong><xsl:value-of select="translate(/GSP/PARAM[@name='q']/@original_value,'+',' ')"/></strong> - did not match any documents.</p>
				<p>Suggestions:</p>
				<ul>
					<li>Make sure all words are spelled correctly.</li>
					<li>Try different keywords.</li>
					<li>Try more general keywords.</li>
					<li>Try fewer keywords.</li>
				</ul>
			</div>
			<div class="acgc_clear">&#160;</div>
			<div class="acgc_content_box_footer acgc_relative">&#160;</div>
			<div class="acgc_clear">&#160;</div>
		</div>
		<div class="acgc_content_box_bottom_decal">&#160;</div>
</div>	
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>
<xsl:variable name="currentPage" select="/GSP/PARAM[@name='page']/@original_value"/>
<xsl:variable name="portalServer"><xsl:value-of select="$search_env"/></xsl:variable>

<xsl:template name="resultsBar">
<div id="acgc_recordsorter" class="acgc_relative">
	<div class="acgc_sort_by">
		Results Per Page: <span class="acgc_sort_by_focus" id="span_numofResults"><xsl:value-of select="/GSP/PARAM[@name='num']/@original_value"/>&#160;Results <img src="/AuroraTheme/themes/html/assets/images/arrow-down-small-header.png" alt="arrow" class="acgc_inline_icon" /></span>
		<div class="acgc_relative">
			<div class="acgc_sort_by_holder">
				<ul>
					<li>
						<a href="#newest" title="10 Results" onclick="javascript:changePageSize(10)">
							<input name="selected" onclick="this.form.submit()" value="locations" type="radio">
							<xsl:if test="/GSP/PARAM[@name='num']/@original_value = 10">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
							</input>&#160; 10 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="20 Results" onclick="javascript:changePageSize(20)">
							<input name="selected" onclick="this.form.submit()" value="doctors" type="radio">
							<xsl:if test="/GSP/PARAM[@name='num']/@original_value = 20">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
							</input>&#160; 20 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="50 Results" onclick="javascript:changePageSize(50)">
							<input name="selected" onclick="this.form.submit()" value="healthinfo" type="radio">
							<xsl:if test="/GSP/PARAM[@name='num']/@original_value = 50">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
							</input>&#160; 50 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="100 Results" onclick="javascript:changePageSize(100)">
							<input name="selected" onclick="this.form.submit()" value="services" type="radio">
							<xsl:if test="/GSP/PARAM[@name='num']/@original_value = 100">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
							</input>&#160; 100 Results
						</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="acgc_relative acgc_pagination_header_holder">
	<div class="acgc_pagination_block">
		<xsl:call-template name="paging">
				<xsl:with-param name="numberOfItems" select="/GSP/RES/M"/>
				<xsl:with-param name="currentPage"  select="$currentPage"/>
				<xsl:with-param name="itemsPerPage"  select="/GSP/PARAM[@name='num']/@original_value"/>					
		</xsl:call-template>
	</div>
</div>
</xsl:template>

<xsl:template name="searchResults">

<div class="acgc_content_box">
	<div class="acgc_spacer_10 acgc_bg_white">&#160;</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10 acgc_bg_white">
		&#160;
		</div>
		<div class="acgc_search_results_left_column">
			<div class="acgc_search_filter">
				<h3>Refine Your Results</h3>
				<ul>
					<xsl:call-template name="collections">
						<xsl:with-param name="selected">default_collection</xsl:with-param>
					</xsl:call-template>
				</ul>
			</div>
			<div class="acgc_related_searches">
				
				<xsl:call-template name="clusterResults"/>

			</div>
		</div>
		<div class="acgc_spacer_10 acgc_bg_white">&#160;</div>
		<div class="acgc_spacer_10 acgc_bg_white">&#160;</div>
		<div class="acgc_search_results_main_column" id="searchResultsBox">
			<xsl:call-template name="results"/>
		</div>
		<div class="acgc_clear">&#160;</div>
		<div class="acgc_content_box_footer acgc_relative">
			&#160;
		</div>
		<div class="acgc_float_right" style="margin-top: -5px;">
			<div class="acgc_pagination_block">
					<xsl:call-template name="paging">
						<xsl:with-param name="numberOfItems" select="/GSP/RES/M"/>
						<xsl:with-param name="currentPage"  select="$currentPage"/>
						<xsl:with-param name="itemsPerPage"  select="/GSP/PARAM[@name='num']/@original_value"/>					
					</xsl:call-template>
			</div>
		</div>
		<div class="acgc_clear">&#160;</div>
	</div>
	<div class="acgc_content_box_bottom_decal">
		&#160;
	</div>	
</div>
</xsl:template>

<xsl:template name="collections">
	<xsl:param name="selected"/>
	<xsl:for-each select="/GSP/COLLECTION">
		<li>
			<xsl:choose>
				<xsl:when test="@collectName = $collection">
					<a href="#filter" title="All Results">
						<xsl:attribute name="onclick">javascript:switchCollection('<xsl:value-of select="@displayName"/>','<xsl:value-of select="@collectName"/>')</xsl:attribute>
						<input type="radio" name="filter" value="all" checked="checked" /><xsl:value-of select="@displayName"/>
					</a>
				</xsl:when>
				<xsl:otherwise>
					<a href="#filter" title="All Results">
						<xsl:attribute name="onclick">javascript:switchCollection('<xsl:value-of select="@displayName"/>','<xsl:value-of select="@collectName"/>')</xsl:attribute>
						<input type="radio" name="filter" value="all" /><xsl:value-of select="@displayName"/>
					</a>
				</xsl:otherwise>
			</xsl:choose>
		</li>
	</xsl:for-each>
</xsl:template>

<xsl:template name="clusterResults">
	<xsl:choose>
		<xsl:when test="/GSP/cluster/gcluster">
			<h3>Related Searches</h3>
			<ul>
			<xsl:for-each select="/GSP/cluster/gcluster">
				<li>
					<a href="#self">
						<xsl:attribute name="onclick">javascript:searchSynonym('<xsl:value-of select="label/@data"/>')</xsl:attribute>
						<xsl:attribute name="title"><xsl:value-of select="label/@data"/></xsl:attribute>
						<xsl:attribute name="ctype">synonym</xsl:attribute>
						<xsl:value-of select="label/@data"/>
					</a>
				</li>
			</xsl:for-each>
			</ul>
		</xsl:when>
		<xsl:otherwise>
		 &#160;
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="results">
	<ul class="acgc_search_results_list">
		<xsl:for-each select="/GSP/RES/R">
			<xsl:choose>
			<xsl:when test="position() mod 2=1">
				<li class="even">
					<strong>
					<xsl:call-template name="createUrl">
							<xsl:with-param name="rank" select="@N"/>
							<xsl:with-param name="title"><xsl:value-of select="T" disable-output-escaping="yes"/></xsl:with-param>
							<xsl:with-param name="linkvalue" ><xsl:value-of select="T" disable-output-escaping="yes"/></xsl:with-param>
							<xsl:with-param name="googleUrl" select="U"/>
					</xsl:call-template>
					</strong>
					<p>
						<xsl:value-of select="S" disable-output-escaping="yes"/>
					</p>
					<xsl:call-template name="createUrl">
							<xsl:with-param name="rank" select="@N"/>
							<xsl:with-param name="title">Visit <xsl:value-of select="U"/></xsl:with-param>
							<xsl:with-param name="linkvalue" select="U"/>
							<xsl:with-param name="googleUrl" select="U"/>
					</xsl:call-template>
				</li>
			</xsl:when>
			<xsl:otherwise>
				<li class="odd">
					<strong>
					<xsl:call-template name="createUrl">
							<xsl:with-param name="rank" select="@N"/>
							<xsl:with-param name="title"><xsl:value-of select="T" disable-output-escaping="yes"/></xsl:with-param>
							<xsl:with-param name="linkvalue" ><xsl:value-of select="T" disable-output-escaping="yes"/></xsl:with-param>
							<xsl:with-param name="googleUrl" select="U"/>
					</xsl:call-template>
					</strong>
					<p>
						<xsl:value-of select="S" disable-output-escaping="yes"/>
					</p>
					<xsl:call-template name="createUrl">
							<xsl:with-param name="rank" select="@N"/>
							<xsl:with-param name="title">Visit <xsl:value-of select="U"/></xsl:with-param>
							<xsl:with-param name="linkvalue" select="U"/>
							<xsl:with-param name="googleUrl" select="U"/>
					</xsl:call-template>
				</li>
			</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</ul>
</xsl:template>

<xsl:template name="createUrl">
	<xsl:param name="rank"/>
	<xsl:param name="title"/>
	<xsl:param name="linkvalue"/>
	<xsl:param name="googleUrl"/>
	<a>
		<xsl:attribute name="href">
			<xsl:call-template name="formatWCMUrl">
				<xsl:with-param name="googleUrl" select="$googleUrl"/>
			</xsl:call-template>						
		</xsl:attribute>
		<xsl:attribute name="rank"><xsl:value-of select="$rank"/></xsl:attribute>
		<xsl:attribute name="ctype">c</xsl:attribute>
		<xsl:attribute name="title"><xsl:value-of select="$title" disable-output-escaping="yes"/></xsl:attribute>
		<xsl:value-of select="$linkvalue" disable-output-escaping="yes"/>
	</a>
</xsl:template>

<xsl:template name="formatWCMUrl">
	<xsl:param name="googleUrl"/>
	<xsl:choose>
		<xsl:when test="contains($googleUrl,$portalServer)">
			<xsl:value-of select="$portalServer"/>/cgc/myportal/connect/?urile=wcm%3apath%3a<xsl:value-of select="substring-after($googleUrl,$portalServer)"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$googleUrl"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="paging">
	<xsl:param name="numberOfItems"/>
	<xsl:param name="currentPage"/>
	<xsl:param name="itemsPerPage"/>
	<xsl:variable name="numberOfPages" select="floor((number($numberOfItems)-1) div $itemsPerPage)+1"/>
	<!-- Calaulate the starting position of the numbers -->
	<xsl:variable name="startPage">
	  <xsl:choose>
		<xsl:when test="$currentPage > 6">
		  <xsl:value-of select="$currentPage - 5"/>
		</xsl:when>
		<xsl:otherwise>
		  <xsl:value-of select="1"/>
		</xsl:otherwise>
	  </xsl:choose>
	</xsl:variable>
	<!-- Calculate the ending position of the numbers -->
	<xsl:variable name="endPage">
	  <xsl:choose>
		<xsl:when test="$numberOfPages - $currentPage > 5">
		  <xsl:value-of select="$currentPage + 5"/>
		</xsl:when>
		<xsl:otherwise>
		  <xsl:value-of select="$numberOfPages"/>
		</xsl:otherwise>
	  </xsl:choose>
	</xsl:variable>
	<xsl:if test="$currentPage > 1">
		<a>
			<xsl:attribute name="href">#</xsl:attribute>
			<xsl:attribute name="ctype">nav.page</xsl:attribute>
			<xsl:attribute name="onclick">changePageNum(this,'1');return false;</xsl:attribute>
			<img src="/AuroraTheme/themes/html/assets/images/arrows-full-left.png" alt="First" />
		</a> &#160;
		<a>
			<xsl:attribute name="href">#</xsl:attribute>
			<xsl:attribute name="ctype">nav.prev</xsl:attribute>
			<xsl:attribute name="onclick">changePageNum(this,'<xsl:value-of select="$currentPage - 1"/>');return false;</xsl:attribute>
			<img src="/AuroraTheme/themes/html/assets/images/arrows-one-left.png" alt="Prev" />
		</a> &#160;
	</xsl:if>
	<xsl:call-template name="tplNumber">
		<xsl:with-param name="current" select="$currentPage"/>
		<xsl:with-param name="number" select="$startPage"/>
		<xsl:with-param name="max" select="$endPage"/>
	</xsl:call-template>
	 <xsl:if test="$currentPage + 5 &lt; $numberOfPages">
      <a>
		  <xsl:attribute name="href">#</xsl:attribute>
		  <xsl:attribute name="ctype">nav.next</xsl:attribute>
		  <xsl:attribute name="onclick">changePageNum(this,'<xsl:value-of select="$currentPage + 6"/>');return false;</xsl:attribute>
		  <img src="/AuroraTheme/themes/html/assets/images/arrows-one-right.png" alt="Next" />
	  </a> &#160;
		<a>
		<xsl:attribute name="href">#</xsl:attribute>
		<xsl:attribute name="ctype">nav.page</xsl:attribute>
		<xsl:attribute name="onclick">changePageNum(this,'<xsl:value-of select="$currentPage + 100"/>');return false;</xsl:attribute>
		<img src="/AuroraTheme/themes/html/assets/images/arrows-full-right.png" alt="Last" /></a>
	</xsl:if>
	
</xsl:template>

<xsl:template name="tplNumber">
  <xsl:param name="current"/>
  <xsl:param name="number"/>
  <xsl:param name="max"/>

  <xsl:choose>
    <xsl:when test="$number = $current">
      <!-- Show current page without a link -->
		<xsl:value-of select="$number"/>&#160;
    </xsl:when>
    <xsl:otherwise>
	<a>
		<xsl:attribute name="href">#</xsl:attribute>
		<xsl:attribute name="ctype">nav.page</xsl:attribute>
		<xsl:attribute name="onclick">changePageNum(this,'<xsl:value-of select="$number"/>');return false;</xsl:attribute>
		<xsl:value-of select="$number"/>
	</a>&#160;
    </xsl:otherwise>
  </xsl:choose>

  <!-- Recursively call the template untill we reach the max number of pages -->
  <xsl:if test="$number &lt; $max">
    <xsl:call-template name="tplNumber">
      <xsl:with-param name="current" select="$current"/>
      <xsl:with-param name="number" select="$number+1"/>
      <xsl:with-param name="max" select="$max"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>


<!-- *** END OF STYLESHEET *** -->