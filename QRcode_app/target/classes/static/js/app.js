async function generateQRCode() {
    const textInput = document.getElementById("textInput");
    const resultContainer = document.getElementById("resultContainer");
    const qrCodeImage = document.getElementById("qrCodeImage");
    const downloadButton = document.getElementById("downloadButton");

    // Get text input
    const text = textInput.value;
    if (!text) {
        alert("Please enter some text or URL.");
        return;
    }

    // Get color selections
    const foregroundColor = document.getElementById("foregroundColor").value;
    const backgroundColor = document.getElementById("backgroundColor").value;

    console.log('Foreground Color:', foregroundColor);  // Log the foreground color
    console.log('Background Color:', backgroundColor);  // Log the background color

    // Show a loading indicator
    const generateButton = document.getElementById("generateButton");
    generateButton.textContent = "Generating...";
    generateButton.disabled = true;

    try {
        // Make the request to the backend with color parameters
        const response = await fetch(
            `/api/qr/generate?text=${encodeURIComponent(text)}&fgColor=${encodeURIComponent(foregroundColor)}&bgColor=${encodeURIComponent(backgroundColor)}`
        );

        if (!response.ok) {
            throw new Error("Error generating QR code");
        }

        // Get the response as plain text (Base64 image data)
        const qrCodeImageUrl = await response.text();

        // Set the image source and show the result container
        qrCodeImage.src = qrCodeImageUrl;
        resultContainer.classList.remove("hidden");

        // Enable the download button and update its link
        downloadButton.style.display = "inline-block"; // Ensure it becomes visible
        downloadButton.href = qrCodeImageUrl; // Set the download link to the Base64 data
    } catch (error) {
        alert("Error generating QR code: " + error.message);
    } finally {
        // Reset the button text and enable it again
        generateButton.textContent = "Generate QR Code";
        generateButton.disabled = false;
    }
}
