<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  version="2.0">
<xsl:output omit-xml-declaration="yes"/>
<xsl:template match="/">
		<script>
			var SN = <xsl:value-of select="/GSP/RES/@SN"/>;
			var EN = <xsl:value-of select="/GSP/RES/@EN"/>;
			var Q = '<xsl:value-of select="/GSP/PARAM[@name='q']/@original_value"/>';
		</script>
		<div class="acgc_top_content_wrap">
			<div class="acgc_top_content_box acgc_relative">
			<h1><span class="acgc_top_content_small_txt">Showing </span><xsl:value-of select="/GSP/RES/M"/><span class="acgc_top_content_small_txt"> search results for </span>&quot;<xsl:value-of select="/GSP/PARAM[@name='q']/@original_value"/>&quot;</h1>
			</div>
		</div>
		<xsl:call-template name="resultsBar"/>
		<xsl:call-template name="searchResults"/>
</xsl:template>

<xsl:template name="resultsBar">
<div id="acgc_recordsorter" class="acgc_relative">
	<div class="acgc_sort_by">
		Results Per Page: <span class="acgc_sort_by_focus" id="span_numofResults">10 Results <img src="/AuroraTheme/themes/html/assets/images/arrow-down-small-header.png" alt="arrow" class="acgc_inline_icon" /></span>
		<div class="acgc_relative">
			<div class="acgc_sort_by_holder">
				<ul>
					<li>
						<a href="#newest" title="10 Results" onclick="javascript:changePageSize(10)">
							<input name="selected" onclick="this.form.submit()" value="locations" checked="checked" type="radio"/>&#160; 10 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="20 Results" onclick="javascript:changePageSize(20)">
							<input name="selected" onclick="this.form.submit()" value="doctors" type="radio"/>&#160; 20 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="50 Results" onclick="javascript:changePageSize(50)">
							<input name="selected" onclick="this.form.submit()" value="healthinfo" type="radio"/>&#160; 50 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="100 Results" onclick="javascript:changePageSize(100)">
							<input name="selected" onclick="this.form.submit()" value="services" type="radio"/>&#160; 100 Results
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
				<xsl:with-param name="currentPage"  select="/GSP/RES/@SN"/>
				<xsl:with-param name="itemsPerPage"  select="/GSP/RES/@EN"/>					
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
					<li>
						<a href="#filter" title="All Results" onclick="javascript: $(this).children('input').prop('checked', 'checked'); /* Submit Form Here */ return false;">
							<input type="radio" name="filter" value="all" checked="checked" /> All Results
						</a>
					</li>
					<li>
						<a href="#filter" title="Locations" onclick="javascript: $(this).children('input').prop('checked', 'checked'); /* Submit Form Here */ return false;">
							<input type="radio" name="filter" value="locations" /> Locations
						</a>
					</li>
					<li>
						<a href="#filter" title="Doctors" onclick="javascript: $(this).children('input').prop('checked', 'checked'); /* Submit Form Here */ return false;">
							<input type="radio" name="filter" value="doctors" /> Doctors
						</a>
					</li>
					<li>
						<a href="#filter" title="Health Information" onclick="javascript: $(this).children('input').prop('checked', 'checked'); /* Submit Form Here */ return false;">
							<input type="radio" name="filter" value="healthinformation" /> Health Information
						</a>
					</li>
					<li>
						<a href="#filter" title="Services" onclick="javascript: $(this).children('input').prop('checked', 'checked'); /* Submit Form Here */ return false;">
							<input type="radio" name="filter" value="services" /> Services
						</a>
					</li>
				</ul>
			</div>
			<div class="acgc_related_searches">
				<h3>Related Searches</h3>
				<ul>
					<li>
						<a href="" title="Woman's Health">
							Woman's Health
						</a>
					</li>
					<li>
						<a href="" title="Woman's Health">
							Men's Health
						</a>
					</li>
				</ul>
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
						<xsl:with-param name="currentPage"  select="/GSP/RES/@SN"/>
						<xsl:with-param name="itemsPerPage"  select="/GSP/RES/@EN"/>					
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
	<xsl:if test="$startPage > 1">
		<a>
			<xsl:attribute name="href">#</xsl:attribute>
			<xsl:attribute name="onclick">changePageNum(this,'1');return false;</xsl:attribute>
			<img src="/AuroraTheme/themes/html/assets/images/arrows-full-left.png" alt="First" />
		</a> &#160;
		<a>
			<xsl:attribute name="href">#</xsl:attribute>
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
		  <xsl:attribute name="onclick">changePageNum(this,'<xsl:value-of select="$currentPage + 6"/>');return false;</xsl:attribute>
		  <img src="/AuroraTheme/themes/html/assets/images/arrows-one-right.png" alt="Next" />
	  </a> &#160;
		<a href="">
		<xsl:attribute name="href">#</xsl:attribute>
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
	<a>
		<xsl:attribute name="href">#</xsl:attribute>
		<xsl:attribute name="onclick">changePageNum(this,'<xsl:value-of select="$number"/>');return false;</xsl:attribute>
		<xsl:attribute name="class">selected</xsl:attribute>
		<xsl:value-of select="$number"/>
	</a>&#160;
    </xsl:when>
    <xsl:otherwise>
	<a>
		<xsl:attribute name="href">#</xsl:attribute>
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