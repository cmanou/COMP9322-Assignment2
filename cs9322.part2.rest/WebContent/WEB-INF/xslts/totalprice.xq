xquery version "1.0";
let $a := sum(doc("event.xml")//price[text() != ''])
return <Price>{$a}</Price>
