<?php
error_reporting(0); // Disable warnings and notices
ini_set('display_errors', 0); // Prevent error output
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");
include('db_connect.php');

// Decode incoming JSON request
$data = json_decode(file_get_contents("php://input"));

$email = $data->username ?? '';
$password = $data->password ?? '';

// Initialize response
$response = array("success" => false, "message" => "");

// Check if email exists in the database
$query = "SELECT * FROM users WHERE email = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();
    // Verify the password
    if (password_verify($password, $user['password'])) {
        // Login successful
        $response["success"] = true;
        $response["message"] = "Login successful";
        $response["user"] = array(
            "id" => $user['id'],
            "first_name" => $user['first_name'],
            "last_name" => $user['last_name'],
            "email" => $user['email'],
            "address" => $user['address']
        );
    } else {
        // Incorrect password
        $response["message"] = "Incorrect password";
    }
} else {
    // User not found
    $response["message"] = "User not found";
}

// Return JSON response
echo json_encode($response);
?>
