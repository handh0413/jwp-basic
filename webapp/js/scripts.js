String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".answerWrite input[type=submit]").click(addAnswer);
$(".qna-comment .form-delete").click(deleteAnswer);

function addAnswer(e) {
	e.preventDefault();
	var queryString = $("form[name=answer]").serialize();
	
	$.ajax({
		type: 'post',
		url: '/api/qna/addAnswer',
		data: queryString,
		dataType: 'json',
		error: onErrorAddAnswer,
		success: onSuccessAddAnswer,
	});
}

function onErrorAddAnswer(status) {
	
}

function onSuccessAddAnswer(json, status) {
	var answerTemplate = $("#answerTemplate").html();
	console.log(json.savedAnswer);
	var template = answerTemplate.format(
			json.savedAnswer.writer, 
			json.savedAnswer.createdDate,
			json.savedAnswer.contents,
			json.savedAnswer.answerId);
	$(".qna-comment-slipp-articles").prepend(template);
}

function deleteAnswer(e) {
	e.preventDefault();
	var target = $(event.target);
	// console.log($(event.target));
	var queryString = $(event.target).parent().serialize();
	$.ajax({
		type: 'post',
		url: '/api/qna/deleteAnswer',
		data: queryString,
		error: onErrorDeleteAnswer,
		context: target,
		success: function(data, status) {
			console.log(data);
			console.log(status);
			target.closest(".article").remove();
		},
	});
}

function onErrorDeleteAnswer(status) {
	
}

function onSuccessDeleteAnswer(json, status) {
	
}


