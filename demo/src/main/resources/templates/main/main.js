const sideNav = document.getElementById("side_nav");
const select_payment = document.querySelector(".select_payment");

const deposit_form = document.getElementById("deposit_form");
const withdraw_form = document.getElementById("withdraw_form");
const transfer_form = document.getElementById("transfer_form");

function toggleNav() {
    sideNav.classList.toggle("is_open");
}


select_payment.addEventListener("change", () => {
    var payment = select_payment.value;
    
    switch (payment) {
        case "select":
            deposit_form.style.display = "none";
            withdraw_form.style.display = "none";
            transfer_form.style.display = "none";
            break;

        case "deposit":
            deposit_form.style.display = "block";
            withdraw_form.style.display = "none";
            transfer_form.style.display = "none";
            break;

        case "withdraw":
            deposit_form.style.display = "none"
            withdraw_form.style.display = "block";
            transfer_form.style.display = "none";
            break;

        case "transfer":
            deposit_form.style.display = "none";
            withdraw_form.style.display = "none";
            transfer_form.style.display = "block";
            break;
    }
});
