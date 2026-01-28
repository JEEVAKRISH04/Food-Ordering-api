<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign-Up</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-r from-orange-500 to-orange-700 flex justify-center items-center min-h-screen relative overflow-hidden">

    <!-- Logo -->
    <div class="absolute top-8 left-8 flex items-center space-x-6">
        <img src="images/52.jpg"
            class="transition-transform duration-300 hover:scale-110 w-14 h-14 rounded-full"
            alt="Logo">
    </div>

    <!-- Sign-Up Form -->
    <div class="bg-white shadow-lg rounded-xl p-8 m-3 w-full max-w-md">
        <h2 class="text-center text-black text-3xl font-bold mb-4">Sign Up</h2>
        <p class="text-center text-sm text-gray-600 mb-6">Already have an account? 
            <a href="login" class="text-orange-500 font-medium hover:underline">Login here</a>
        </p>

        <form action="SignUpServlet" method="post" class="space-y-4">
            <input type="text" id="name" name="name"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required placeholder="Enter your name">

            <input type="email" id="email" name="email"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required placeholder="Enter your email">

            <input type="tel" id="phoneNo" name="phoneNo"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required placeholder="Enter your phone number">

            <input type="text" id="username" name="username"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required placeholder="Enter your username">

            <select name="role" id="role"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required>
                <option value="" disabled selected>Select Role</option>
               ]
                    <option value="CUSTOMER">Customer</option>
                    <option value="ADMIN">Admin</option>
                    <option value="DELIVERY_BOY">Delivery Agent</option>
            </select>

            <input type="password" id="password" name="password"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required placeholder="Enter your password">

            <input type="text" id="address" name="address"
                class="w-full p-3 border border-gray-300 rounded focus:outline-none focus:border-orange-500"
                required placeholder="Enter your address">

            <button type="submit"
                class="w-full mt-4 p-3 bg-orange-500 text-white rounded-lg font-semibold cursor-pointer transition-all duration-300 hover:bg-orange-700">
                Sign Up
            </button>

            <p class="text-center text-xs text-gray-600 mt-4">
                By signing up, you accept the <a href="#" class="text-orange-500 font-medium hover:underline">Terms & Conditions</a> and 
                <a href="#" class="text-orange-500 font-medium hover:underline">Privacy Policy</a>
            </p>
        </form>
    </div>
</body>
</html>
