<?php

$client = new SoapClient('http://agtajhotel.com/Restaurent/api/soap/?wsdl');

// If somestuff requires api authentification,
// then get a session token
$session = $client->login('mukul', 'key123');

echo $session;

?>