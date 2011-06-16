package com.google.xmldiff

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class ComparisonSuite extends FunSuite with ShouldMatchers {
  val comparator = new Comparison

  test("comparing equal xml nodes") {
    comparator(masterXml, masterXml) should be (NoDiff)
  }

  test ("comparing unequal xml nodes") {
    comparator(masterXml, liftRestXml) should be (NoDiff)
  }

  test ("doings a method method specific comparison of 2 maps") {
    val original = Map("nintendo" -> "wii", "microsoft" -> "xbox 360")

    val hotnspicy = Map("nintendo" -> "wiiu", "sony" -> "playstation")

    val expected = """|- nintendo: wii
                      |+ nintendo: wiiu""".stripMargin

    comparator.reportChangedMapValues(original, hotnspicy) should be (expected)
  }

  val masterXml = <response status="ok" total="16277" start-index="1" user-tier="partner" page-size="10" current-page="1" pages="1628" order-by="newest">
      <tag type="keyword" web-title="Rugby union" id="sport/rugby-union" api-url="http://localhost:8700/content-api/api/sport/rugby-union" web-url="http://www.guardian.co.uk/sport/rugby-union" section-id="sport" section-name="Sport"/> <results>
      <content id="sport/2011/jun/09/aviva-stadium-2013-heineken-cup" section-id="sport" section-name="Sport" web-publication-date="2011-06-09T13:03:13+01:00" web-title="Aviva Stadium confirmed as host for the 2013 Heineken Cup final" web-url="http://www.guardian.co.uk/sport/2011/jun/09/aviva-stadium-2013-heineken-cup" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/aviva-stadium-2013-heineken-cup">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/SPORT/Pix/pictures/2011/6/9/1307620936361/Aviva-Stadium-003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/09/saracens-joe-maddock-benetton-treviso" section-id="sport" section-name="Sport" web-publication-date="2011-06-09T12:22:50+01:00" web-title="Saracens complete signing of winger Joe Maddock from Benetton Treviso" web-url="http://www.guardian.co.uk/sport/2011/jun/09/saracens-joe-maddock-benetton-treviso" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/saracens-joe-maddock-benetton-treviso">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/SPORT/Pix/pictures/2011/6/9/1307618516572/Joe-Maddock-003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/09/leicester-tigers-kitchener-young-brookes" section-id="sport" section-name="Sport" web-publication-date="2011-06-09T12:11:18+01:00" web-title="Leicester sign Graham Kitchener, Micky Young and Kieran Brookes" web-url="http://www.guardian.co.uk/sport/2011/jun/09/leicester-tigers-kitchener-young-brookes" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/leicester-tigers-kitchener-young-brookes">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/SPORT/Pix/pictures/2011/6/9/1307617796965/Graham-Kitchener-003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/09/john-steele-investec-rfu-sponsorship" section-id="sport" section-name="Sport" web-publication-date="2011-06-09T11:31:50+01:00" web-title="More pressure on John Steele as Investec end RFU sponsorship deal" web-url="http://www.guardian.co.uk/sport/2011/jun/09/john-steele-investec-rfu-sponsorship" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/john-steele-investec-rfu-sponsorship">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/5/20/1305925844792/John-Steele-chief-executi-001.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/09/rfu-john-steele-clive-woodward" section-id="sport" section-name="Sport" web-publication-date="2011-06-09T00:35:00+01:00" web-title="RFU says John Steele is not on way out over Sir Clive Woodward affair" web-url="http://www.guardian.co.uk/sport/2011/jun/09/rfu-john-steele-clive-woodward" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/rfu-john-steele-clive-woodward">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/5/20/1305925844792/John-Steele-chief-executi-001.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/09/russia-canada-churchill-cup-esher" section-id="sport" section-name="Sport" web-publication-date="2011-06-09T00:27:05+01:00" web-title="Russia push Canada before going down to Churchill Cup defeat at Esher" web-url="http://www.guardian.co.uk/sport/2011/jun/09/russia-canada-churchill-cup-esher" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/russia-canada-churchill-cup-esher">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/9/1307575006596/vasili-artemiev-003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/08/danny-cipriani-melbourne-rebels" section-id="sport" section-name="Sport" web-publication-date="2011-06-08T11:31:42+01:00" web-title="Danny Cipriani returns to starting line-up for Melbourne Rebels" web-url="http://www.guardian.co.uk/sport/2011/jun/08/danny-cipriani-melbourne-rebels" api-url="http://localhost:8700/content-api/api/sport/2011/jun/08/danny-cipriani-melbourne-rebels">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/8/1307527396563/Danny-Cipriani-Melbourne--003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/07/northampton-munster-castres-heineken-cup" section-id="sport" section-name="Sport" web-publication-date="2011-06-07T18:42:39+01:00" web-title="Northampton drawn with Munster and Castres in Heineken Cup pool" web-url="http://www.guardian.co.uk/sport/2011/jun/07/northampton-munster-castres-heineken-cup" api-url="http://localhost:8700/content-api/api/sport/2011/jun/07/northampton-munster-castres-heineken-cup">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Admin/BkFill/Default_image_group/2011/6/7/1307467614291/Jim-Mallinder--003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jun/07/james-haskell-paul-sackey-stade-francais" section-id="sport" section-name="Sport" web-publication-date="2011-06-07T17:52:59+01:00" web-title="James Haskell out, Paul Sackey in as Stade Français shake things up" web-url="http://www.guardian.co.uk/sport/2011/jun/07/james-haskell-paul-sackey-stade-francais" api-url="http://localhost:8700/content-api/api/sport/2011/jun/07/james-haskell-paul-sackey-stade-francais">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/7/1307465227437/Englands-James-Haskell-003.jpg</field>
        </fields>
      </content> <content id="sport/blog/2011/jun/07/olympic-russia-rugby-union-world-cup" section-id="sport" section-name="Sport" web-publication-date="2011-06-07T14:15:12+01:00" web-title="Russia head for World Cup powered by a Welshman and Olympic gold" web-url="http://www.guardian.co.uk/sport/blog/2011/jun/07/olympic-russia-rugby-union-world-cup" api-url="http://localhost:8700/content-api/api/sport/blog/2011/jun/07/olympic-russia-rugby-union-world-cup">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/7/1307452241719/Russias-rugby-union-team-003.jpg</field>
        </fields>
      </content>
    </results> <lead-content>
      <content id="world/2011/mar/30/mystery-rugby-player-arrested-decapitating-man" section-id="world" section-name="World news" web-publication-date="2011-03-30T21:33:33+01:00" web-title="Mystery rugby player in Pretoria arrested over decapitation" web-url="http://www.guardian.co.uk/world/2011/mar/30/mystery-rugby-player-arrested-decapitating-man" api-url="http://localhost:8700/content-api/api/world/2011/mar/30/mystery-rugby-player-arrested-decapitating-man">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Admin/BkFill/Default_image_group/2011/3/30/1301508033075/Blue-Balls-player-Bees-Ro-003.jpg</field>
        </fields>
      </content> <content id="sport/2011/jan/21/rugby-physio-high-court-victory-bloodgate" section-id="sport" section-name="Sport" web-publication-date="2011-01-21T16:30:11Z" web-title="High court victory for rugby physio struck off in 'bloodgate' affair" web-url="http://www.guardian.co.uk/sport/2011/jan/21/rugby-physio-high-court-victory-bloodgate" api-url="http://localhost:8700/content-api/api/sport/2011/jan/21/rugby-physio-high-court-victory-bloodgate">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2011/1/21/1295624746705/Steph-Brennan-003.jpg</field>
        </fields>
      </content>
    </lead-content>
  </response>

  val liftRestXml = <response user-tier="partner" current-page="1" start-index="1" order-by="newest" page-size="10" pages="1628" total="16277" status="ok">
      <tag type="keyword" web-title="Rugby union" section-name="Sport" id="sport/rugby-union" api-url="http://localhost:8700/content-api/api/sport/rugby-union" section-id="sport" web-url="http://www.guardian.co.uk/sport/rugby-union"/> <results>
      <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/aviva-stadium-2013-heineken-cup" web-publication-date="2011-06-09T12:03:13Z" id="sport/2011/jun/09/aviva-stadium-2013-heineken-cup" section-name="Sport" web-title="Aviva Stadium confirmed as host for the 2013 Heineken Cup final" web-url="http://www.guardian.co.uk/sport/2011/jun/09/aviva-stadium-2013-heineken-cup">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/SPORT/Pix/pictures/2011/6/9/1307620936361/Aviva-Stadium-003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/saracens-joe-maddock-benetton-treviso" web-publication-date="2011-06-09T11:22:50Z" id="sport/2011/jun/09/saracens-joe-maddock-benetton-treviso" section-name="Sport" web-title="Saracens complete signing of winger Joe Maddock from Benetton Treviso" web-url="http://www.guardian.co.uk/sport/2011/jun/09/saracens-joe-maddock-benetton-treviso">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/SPORT/Pix/pictures/2011/6/9/1307618516572/Joe-Maddock-003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/leicester-tigers-kitchener-young-brookes" web-publication-date="2011-06-09T11:11:18Z" id="sport/2011/jun/09/leicester-tigers-kitchener-young-brookes" section-name="Sport" web-title="Leicester sign Graham Kitchener, Micky Young and Kieran Brookes" web-url="http://www.guardian.co.uk/sport/2011/jun/09/leicester-tigers-kitchener-young-brookes">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/SPORT/Pix/pictures/2011/6/9/1307617796965/Graham-Kitchener-003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/john-steele-investec-rfu-sponsorship" web-publication-date="2011-06-09T10:31:50Z" id="sport/2011/jun/09/john-steele-investec-rfu-sponsorship" section-name="Sport" web-title="More pressure on John Steele as Investec end RFU sponsorship deal" web-url="http://www.guardian.co.uk/sport/2011/jun/09/john-steele-investec-rfu-sponsorship">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/5/20/1305925844792/John-Steele-chief-executi-001.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/rfu-john-steele-clive-woodward" web-publication-date="2011-06-08T23:35:00Z" id="sport/2011/jun/09/rfu-john-steele-clive-woodward" section-name="Sport" web-title="RFU says John Steele is not on way out over Sir Clive Woodward affair" web-url="http://www.guardian.co.uk/sport/2011/jun/09/rfu-john-steele-clive-woodward">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/5/20/1305925844792/John-Steele-chief-executi-001.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/09/russia-canada-churchill-cup-esher" web-publication-date="2011-06-08T23:27:05Z" id="sport/2011/jun/09/russia-canada-churchill-cup-esher" section-name="Sport" web-title="Russia push Canada before going down to Churchill Cup defeat at Esher" web-url="http://www.guardian.co.uk/sport/2011/jun/09/russia-canada-churchill-cup-esher">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/9/1307575006596/vasili-artemiev-003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/08/danny-cipriani-melbourne-rebels" web-publication-date="2011-06-08T10:31:42Z" id="sport/2011/jun/08/danny-cipriani-melbourne-rebels" section-name="Sport" web-title="Danny Cipriani returns to starting line-up for Melbourne Rebels" web-url="http://www.guardian.co.uk/sport/2011/jun/08/danny-cipriani-melbourne-rebels">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/8/1307527396563/Danny-Cipriani-Melbourne--003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/07/northampton-munster-castres-heineken-cup" web-publication-date="2011-06-07T17:42:39Z" id="sport/2011/jun/07/northampton-munster-castres-heineken-cup" section-name="Sport" web-title="Northampton drawn with Munster and Castres in Heineken Cup pool" web-url="http://www.guardian.co.uk/sport/2011/jun/07/northampton-munster-castres-heineken-cup">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Admin/BkFill/Default_image_group/2011/6/7/1307467614291/Jim-Mallinder--003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jun/07/james-haskell-paul-sackey-stade-francais" web-publication-date="2011-06-07T16:52:59Z" id="sport/2011/jun/07/james-haskell-paul-sackey-stade-francais" section-name="Sport" web-title="James Haskell out, Paul Sackey in as Stade Français shake things up" web-url="http://www.guardian.co.uk/sport/2011/jun/07/james-haskell-paul-sackey-stade-francais">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/7/1307465227437/Englands-James-Haskell-003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/blog/2011/jun/07/olympic-russia-rugby-union-world-cup" web-publication-date="2011-06-07T13:15:12Z" id="sport/blog/2011/jun/07/olympic-russia-rugby-union-world-cup" section-name="Sport" web-title="Russia head for World Cup powered by a Welshman and Olympic gold" web-url="http://www.guardian.co.uk/sport/blog/2011/jun/07/olympic-russia-rugby-union-world-cup">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Sport/Pix/pictures/2011/6/7/1307452241719/Russias-rugby-union-team-003.jpg</field>
        </fields>
      </content>
    </results> <lead-content>
      <content section-id="world" api-url="http://localhost:8700/content-api/api/world/2011/mar/30/mystery-rugby-player-arrested-decapitating-man" web-publication-date="2011-03-30T20:33:33Z" id="world/2011/mar/30/mystery-rugby-player-arrested-decapitating-man" section-name="World news" web-title="Mystery rugby player in Pretoria arrested over decapitation" web-url="http://www.guardian.co.uk/world/2011/mar/30/mystery-rugby-player-arrested-decapitating-man">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Admin/BkFill/Default_image_group/2011/3/30/1301508033075/Blue-Balls-player-Bees-Ro-003.jpg</field>
        </fields>
      </content> <content section-id="sport" api-url="http://localhost:8700/content-api/api/sport/2011/jan/21/rugby-physio-high-court-victory-bloodgate" web-publication-date="2011-01-21T16:30:11Z" id="sport/2011/jan/21/rugby-physio-high-court-victory-bloodgate" section-name="Sport" web-title="High court victory for rugby physio struck off in 'bloodgate' affair" web-url="http://www.guardian.co.uk/sport/2011/jan/21/rugby-physio-high-court-victory-bloodgate">
        <fields>
          <field name="thumbnail">http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2011/1/21/1295624746705/Steph-Brennan-003.jpg</field>
        </fields>
      </content>
    </lead-content>
  </response>
}