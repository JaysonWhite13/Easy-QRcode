async function generateQRCode() {
    console.log("🔹 generateQRCode() function called"); // Debug log

    const textInput = document.getElementById("textInput");
    const text = textInput.value;
    console.log("📌 Text Input:", text);

    if (!text) {
        alert("Please enter some text or URL.");
        return;
    }

    // Debugging colors
    const foregroundColor = document.getElementById("foregroundColor").value;
    const backgroundColor = document.getElementById("backgroundColor").value;
    console.log("🎨 Foreground Color:", foregroundColor);
    console.log("🎨 Background Color:", backgroundColor);

    try {
        console.log("🚀 Sending request to backend...");

        const response = await fetch(`/api/qr/generate?text=${encodeURIComponent(text)}&fgColor=${encodeURIComponent(foregroundColor)}&bgColor=${encodeURIComponent(backgroundColor)}`);

        console.log("✅ Request sent, waiting for response...");

        if (!response.ok) {
            throw new Error("Error generating QR code");
        }

        const qrCodeImageUrl = await response.text();
        console.log("🖼️ QR Code Generated:", qrCodeImageUrl);

        document.getElementById("qrCodeImage").src = qrCodeImageUrl;
        document.getElementById("resultContainer").classList.remove("hidden");

    } catch (error) {
        console.error("❌ Error generating QR code:", error);
        alert("Error: " + error.message);
    }
}
