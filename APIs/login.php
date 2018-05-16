<?php

$proxy = new SoapClient('http://agtajhotel.com/Restaurent/api/v2_soap/?wsdl');

// If somestuff requires api authentification,
// then get a session token
$session = $_POST['session_id'];


$result = $proxy->catalogCategoryTree($session , 1);

$json = json_encode($result);

echo $json;



?>