document.addEventListener("DOMContentLoaded", () => {
  const result = document.querySelector(".result");
  if (result && result.textContent.trim() !== "") {
    result.classList.add("visible");
  }
});
