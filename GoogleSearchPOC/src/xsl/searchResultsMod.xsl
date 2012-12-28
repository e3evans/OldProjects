<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  version="2.0">
<xsl:output omit-xml-declaration="yes"/>
<xsl:template match="/">
	
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
		Results Per Page: <span class="acgc_sort_by_focus">10 Results <img src="/AuroraTheme/themes/html/assets/images/arrow-down-small-header.png" alt="arrow" class="acgc_inline_icon" /></span>
		<div class="acgc_relative">
			<div class="acgc_sort_by_holder">
				<ul>
					<li>
						<a href="#newest" title="10 Results" onclick="javascript: $(this).children('input').prop('checked', 'checked'); this.form.submit(); return false;">
							<input name="selected" onclick="this.form.submit()" value="locations" checked="checked" type="radio"/>&#160; 10 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="20 Results" onclick="javascript: $(this).children('input').prop('checked', 'checked'); this.form.submit(); return false;">
							<input name="selected" onclick="this.form.submit()" value="doctors" type="radio"/>&#160; 20 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="50 Results" onclick="javascript: $(this).children('input').prop('checked', 'checked'); this.form.submit(); return false;">
							<input name="selected" onclick="this.form.submit()" value="healthinfo" type="radio"/>&#160; 50 Results
						</a>
					</li>
					<li>
						<a href="#oldest" title="100 Results" onclick="javascript: $(this).children('input').prop('checked', 'checked'); this.form.submit(); return false;">
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
		<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-full-left.png" alt="First" /></a> &#160;
		<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-one-left.png" alt="Prev" /></a> &#160;
		<a href="">1</a> &#160;
		<a href="">2</a> &#160;
		<a href="">3</a> &#160;
		4 &#160;
		<a href="">8</a> &#160;
		<a href="">9</a> &#160;
		<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-one-right.png" alt="Next" /></a> &#160;
		<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-full-right.png" alt="Last" /></a>
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
		<div class="acgc_search_results_main_column">
			<ul class="acgc_search_results_list">
				<li class="even">
					<strong>
						<a href="" title="Maecenas nunc in justo rhoncus aliquam">
							Maecenas nunc in justo rhoncus aliquam
						</a>
					</strong>
					<p>Aliquam interdum pulvinar nibh. Maecenas nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. as nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing...</p>
					<a href="" title="Visit http://caregiverconnect.aurora.org/link/to/page" target="_blank">http://caregiverconnect.aurora.org/link/to/page</a>
				</li>
				<li class="odd">
					<strong>
						<a href="" title="Maecenas nunc in justo rhoncus aliquam">
							Maecenas nunc in justo rhoncus aliquam
						</a>
					</strong>
					<p>Aliquam interdum pulvinar nibh. Maecenas nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. as nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing...</p>
					<a href="" title="Visit http://caregiverconnect.aurora.org/link/to/page" target="_blank">http://caregiverconnect.aurora.org/link/to/page</a>
				</li>
				<li class="even">
					<strong>
						<a href="" title="Maecenas nunc in justo rhoncus aliquam">
							Maecenas nunc in justo rhoncus aliquam
						</a>
					</strong>
					<p>Aliquam interdum pulvinar nibh. Maecenas nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. as nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing...</p>
					<a href="" title="Visit http://caregiverconnect.aurora.org/link/to/page" target="_blank">http://caregiverconnect.aurora.org/link/to/page</a>
				</li>
				<li class="odd">
					<strong>
						<a href="" title="Maecenas nunc in justo rhoncus aliquam">
							Maecenas nunc in justo rhoncus aliquam
						</a>
					</strong>
					<p>Aliquam interdum pulvinar nibh. Maecenas nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. as nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing...</p>
					<a href="" title="Visit http://caregiverconnect.aurora.org/link/to/page" target="_blank">http://caregiverconnect.aurora.org/link/to/page</a>
				</li>
				<li class="even">
					<strong>
						<a href="" title="Maecenas nunc in justo rhoncus aliquam">
							Maecenas nunc in justo rhoncus aliquam
						</a>
					</strong>
					<p>Aliquam interdum pulvinar nibh. Maecenas nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. as nunc in justo rhoncus aliquam. Lorem ipsum dolor sit amet, consectetuer adipiscing...</p>
					<a href="" title="Visit http://caregiverconnect.aurora.org/link/to/page" target="_blank">http://caregiverconnect.aurora.org/link/to/page</a>
				</li>
			</ul>
		</div>
		<div class="acgc_clear">&#160;</div>
		<div class="acgc_content_box_footer acgc_relative">
			&#160;
		</div>
		<div class="acgc_float_right" style="margin-top: -5px;">
			<div class="acgc_pagination_block">
				<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-full-left.png" alt="First" /></a> &#160;
				<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-one-left.png" alt="Prev" /></a> &#160;
				<a href="">1</a> &#160;
				<a href="">2</a> &#160;
				<a href="">3</a> &#160;
				4 &#160;
				<a href="">8</a> &#160;
				<a href="">9</a> &#160;
				<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-one-right.png" alt="Next" /></a> &#160;
				<a href=""><img src="/AuroraTheme/themes/html/assets/images/arrows-full-right.png" alt="Last" /></a>
			</div>
		</div>
		<div class="acgc_clear">&#160;</div>
	</div>
	<div class="acgc_content_box_bottom_decal">
		&#160;
	</div>	
</div>


</xsl:template>

</xsl:stylesheet>


<!-- *** END OF STYLESHEET *** -->



