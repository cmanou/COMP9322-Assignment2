<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fn="fn"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  version="2.0" exclude-result-prefixes="xs fn">

<xsl:output indent="yes" encoding="US-ASCII" />

<xsl:param name="pathToCSV" select="'file:///c:/csv.csv'" />

<xsl:function name="fn:getTokens" as="xs:string+">
    <xsl:param name="str" as="xs:string" />
    <xsl:analyze-string select="concat($str, ',')" regex='(("[^"]*")+|[^,]*),'>
        <xsl:matching-substring>
        <xsl:sequence select='replace(regex-group(1), "^""|""$|("")""", "$1")' />
        </xsl:matching-substring>
    </xsl:analyze-string>
</xsl:function>

<xsl:template match="/" name="main">
    <xsl:choose>
    <xsl:when test="unparsed-text-available($pathToCSV)">
        <xsl:variable name="csv" select="unparsed-text($pathToCSV)" />
        <xsl:variable name="lines" select="tokenize($csv, '\n')" as="xs:string+" />
        <eventList>
        <xsl:for-each select="$lines[position() &gt; 1]">
          <xsl:variable name="lineItems" select="fn:getTokens(.)" as="xs:string+" />
  <xsl:if test="$lineItems[5] = 'Trade'">
            <event>

            <xsl:for-each select="$lineItems">
                <xsl:variable name="pos" select="position()" />
                <xsl:choose>
                  <xsl:when test ="$pos = 1">
                    <stock><xsl:value-of select ="." /></stock>
                  </xsl:when>
                  <xsl:when test ="$pos = 2">
                    <date><xsl:value-of select ="." /></date>
                  </xsl:when>
                  <xsl:when test ="$pos = 3">
                    <time><xsl:value-of select ="." /></time>
                  </xsl:when>
                  <xsl:when test ="$pos = 4">
                    <gmtOffset><xsl:value-of select ="." /></gmtOffset>
                  </xsl:when>
                  <xsl:when test ="$pos = 5">
                    <type><xsl:value-of select ="." /></type>
                  </xsl:when>
                  <xsl:when test ="$pos = 6">
                    <price><xsl:value-of select ="." /></price>
                  </xsl:when>
                  <xsl:when test ="$pos = 7">
                    <volume><xsl:value-of select ="." /></volume>
                  </xsl:when>
                  <xsl:when test ="$pos = 8">
                    <bidPrice><xsl:value-of select ="." /></bidPrice>
                  </xsl:when>
                  <xsl:when test ="$pos = 9">
                    <bidSize><xsl:value-of select ="." /></bidSize>
                  </xsl:when>
                  <xsl:when test ="$pos = 10">
                    <askPrice><xsl:value-of select ="." /></askPrice>
                  </xsl:when>
                  <xsl:when test ="$pos = 11">
                    <askSize><xsl:value-of select ="." /></askSize>
                  </xsl:when>
                  <xsl:otherwise>
                  </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>

          </event>

        </xsl:if>

        </xsl:for-each>
      </eventList>
    </xsl:when>
    <xsl:otherwise>
        <xsl:text>Cannot locate : </xsl:text><xsl:value-of select="$pathToCSV" />
    </xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>
