<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>

    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .bg-opacity-custom {
            background: linear-gradient(to right, rgba(251, 146, 60, 0.8), rgba(253, 224, 71, 0.8));
        }
    </style>
</head>

<body class="bg-gradient-to-r from-orange-500 to-yellow-400 flex items-center justify-center h-screen">

    <div class="form-container shadow-lg rounded-lg p-8 w-full max-w-md bg-opacity-20 backdrop-blur-lg bg-white">
        <!-- Logo Section -->
        <div class="flex items-center justify-center mb-4">
            <img src="images/52.jpg"
                class="transition-transform duration-300 hover:scale-110 w-16 h-16 rounded-full"
                alt="Logo">
        </div>

        <h2 class="text-3xl font-bold text-center text-gray-800 mb-4">Login</h2>
        <p class="text-center text-black-600 mb-4">Or <a href="signup" class="text-orange-600 hover:underline">Create Account</a></p>

        <!-- Error Message -->
        <% String error = request.getParameter("error"); %>
        <% if (error != null) { %>
            <div class="alert alert-danger text-black" role="alert">
                <% if (error.equals("max_attempts")) { %>
                    Maximum login attempts reached. Please try again later.
                <% } else if (error.equals("invalid_credientials")) { %>
                    Invalid credentials. Please check your username and password.
                <% } %>
            </div>
        <% } %>

        <!-- Form -->
        <form action="./LoginServlet" method="post" class="space-y-4">
            <div>
                <input type="text" id="username" name="username" required placeholder="Enter Username"
                    class="w-full p-2 bg-transparent border-b-2 border-gray-400 focus:border-orange-500 focus:outline-none">
            </div>
            <div>
                <input type="password" id="password" name="password" required placeholder="Enter Password"
                    class="w-full p-2 bg-transparent border-b-2 border-gray-400 focus:border-orange-500 focus:outline-none">
            </div>

            <!-- Role Dropdown -->
            <div>
                <select id="role" name="role" required
                    class="w-full p-2 bg-transparent border-b-2 border-gray-400 focus:border-orange-500 focus:outline-none">
                    <option value="">Select Role</option>
                    <option value="CUSTOMER">Customer</option>
                    <option value="ADMIN">Admin</option>
                    <option value="DELIVERY_BOY">Delivery Agent</option>
                    
                </select>
            </div>

            <!-- Login Button -->
            <button type="submit"
                class="w-full bg-orange-500 text-white py-2 rounded-lg hover:bg-orange-600 transition">Login</button>
        </form>

        <!-- Terms -->
        <p class="text-center text-sm text-gray-600 mt-4">
            By clicking on Login, I accept the <a href="#" class="text-black font-medium hover:underline">Terms and Conditions</a>
            and <a href="#" class="text-black font-medium hover:underline">Privacy Policy</a>
        </p>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
