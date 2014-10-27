<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fn="fn"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  version="2.0" exclude-result-prefixes="xs fn">

<xsl:output method="text" indent="no"/>

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


        <xsl:text>{ "events": [ </xsl:text>
        <xsl:for-each select="$lines[position() &gt; 1]">
          <xsl:if test=". != ''">
            <xsl:variable name="lineItems" select="fn:getTokens(.)" as="xs:string+" />

            <xsl:if test="$lineItems[5] = 'Quote'">

            <xsl:if test="position() != 1" >
              <xsl:variable name="lineItems" select="$lines[position() - 1]" as="xs:string+" />

              <xsl:if test="position() != 1" >
                <xsl:text>,</xsl:text>
              </xsl:if>
            </xsl:if>


            <xsl:text>{</xsl:text>
            <xsl:for-each select="$lineItems">
              <xsl:variable name="pos" select="position()" />
              <xsl:choose>
                <xsl:when test ="$pos = 1">
                  <xsl:text> "stock": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 2">
                  <xsl:text> "date": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 3">
                  <xsl:text> "time": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 4">
                  <xsl:text> "gmtOffset": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 5">
                  <xsl:text> "type": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 6">
                  <xsl:text> "price": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 7">
                  <xsl:text> "volume": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 8">
                  <xsl:text> "bidPrice": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 9">
                  <xsl:text> "bidSize": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 10">
                  <xsl:text> "askPrice": "</xsl:text><xsl:value-of select ="." /><xsl:text>",</xsl:text>
                </xsl:when>
                <xsl:when test ="$pos = 11">
                  <xsl:text> "askSize": "</xsl:text><xsl:value-of select ="." /><xsl:text>"</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
            <xsl:text> }</xsl:text>
          </xsl:if>
          </xsl:if>
        </xsl:for-each>
      <xsl:text>]}</xsl:text>
    </xsl:when>
    <xsl:otherwise>
        <xsl:text>Cannot locate : </xsl:text><xsl:value-of select="$pathToCSV" />
    </xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>
