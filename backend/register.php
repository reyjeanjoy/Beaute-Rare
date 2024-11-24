<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

include('db_connect.php');

// Get JSON input from request body
$data = json_decode(file_get_contents("php://input"));

$first_name = $data->first_name ?? '';
$last_name = $data->last_name ?? '';
$email = $data->email ?? '';
$password = $data->password ?? '';
$address = $data->address ?? '';

$response = array("success" => false, "message" => "");

// Check if email already exists
$query = "SELECT * FROM users WHERE email = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $response["message"] = "Email already exists!";
} else {
    // Hash the password
    $hashed_password = password_hash($password, PASSWORD_BCRYPT);

    // Insert the new user
    $insert_query = "INSERT INTO users (first_name, last_name, email, password, address) VALUES (?, ?, ?, ?, ?)";
    $insert_stmt = $conn->prepare($insert_query);
    $insert_stmt->bind_param("sssss", $first_name, $last_name, $email, $hashed_password, $address);

    if ($insert_stmt->execute()) {
        $response["success"] = true;
        $response["message"] = "Registration successful!";
    } else {
        $response["message"] = "Error: Could not register user.";
    }
}

echo json_encode($response);
$conn->close();
?>
