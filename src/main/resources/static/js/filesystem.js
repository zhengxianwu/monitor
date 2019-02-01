/**
 * 
 */
function showPro(value) {
	this.end_value = value;
	if (this.head_value >= 0 && this.head_value <= 30) {
		$("#pro").removeClass("progress-bar bg-success");
	} else if (this.head_value >= 40 && this.head_value <= 50) {
		$("#pro").removeClass("progress-bar bg-info");
	} else if (this.head_value > 50 && this.head_value <= 60) {
		$("#pro").removeClass("progress-bar bg-warning");
	} else if (this.head_value > 60 && this.head_value <= 100) {
		$("#pro").removeClass("progress-bar bg-danger");
	}
	this.head_value = end_value;

	// $("#pro").css("width", head_value + "%");
	// console.log($("#pro").css("width"));
	// var p = $('#pro').width();
	// var p2 = $('#pro').parent().width();
	// var res = parseFloat(p)/parseFloat(p2)
	// console.log(res);
	// var t = $("#pro").css("width", head_value + "%").text(head_value + "%");
	// console.log(t.val());
	// 1先判断前值，然后删除，再判断后值，添加css

	if (this.end_value && this.end_value <= 40) {
		$("#pro").css("width", this.end_value + "%").text(this.end_value + "%");
		$("#pro").addClass("progress-bar bg-success");
	} else if (this.end_value > 40 && this.end_value <= 50) {
		$("#pro").css("width", this.end_value + "%").text(this.end_value + "%");
		$("#pro").addClass("progress-bar bg-info");
	} else if (this.end_value > 50 && this.end_value <= 60) {
		$("#pro").css("width", this.end_value + "%").text(this.end_value + "%");
		$("#pro").addClass("progress-bar bg-warning");
	} else if (this.end_value > 60 && this.end_value <= 100) {
		$("#pro").css("width", end_value + "%").text(this.end_value + "%");
		$("#pro").addClass("progress-bar bg-danger");
	}
}