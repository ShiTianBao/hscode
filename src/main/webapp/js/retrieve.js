
function retrieve() {
	
	var srcResult = $('#srcResult').val();
    var srcRegion = $('#srcRegion').val();
	var tgtRegion = $('#tgtRegion').val();
	$('#tgtResultTable tbody').html("");
	$("#tgtResultTable tbody").append("<tr><th>转译中...</th><th></th><th></th><th></th><th></th></tr>");
	
	if((srcResult === '')){
		alert('请选择一个！');
		return;
	}
					
	$.ajax({
        cache: true,
        type: 'POST',       
        url: '../../retrieve',
        data: {
            'srcResult': srcResult,
            'srcRegion' : srcRegion,
            'tgtRegion' : tgtRegion
        },
        async: true,
        error: function () {
            console.log('ajax error');          
        },
        success: function (res) {
        	console.log(res);

        	var data = res;
        	console.log(data);
        	var hscode = data['hscode'];
        	var description = data['description'];
        	var added = data['added'];
        	var general = data['general'];
        	var favor = data['favor'];
        	console.log(hscode+description);
        	$('#tgtResultTable tbody').html("");
        	$('#tgtResultTable tbody').append("<tr><th>"+hscode+"</th><th>"+description+"</th><th>"+added+"</th><th>"+general+"</th><th>"+favor+"</th></tr>");

        	//$("#tgtResult").val(hscode + ':' + description);
        //	$('#resultBody').append('<script>$("#tgtResult").val('+hscode + ':' + description+');</script>');
        }
    });
};

function reset() {
	$('#form').get(0).reset();
	
}
