<?php

$proxy = new SoapClient('http://agtajhotel.com/Restaurent/api/v2_soap/?wsdl');

$sessionId = $_POST['session_id'];
$id = $_POST['id'];


$result = $proxy->catalogCategoryAssignedProducts($sessionId, $id);
$json = json_encode($result);

echo $json;



?>