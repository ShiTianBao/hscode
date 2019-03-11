
function retrieve() {
	
	var srcResult = $('#srcResult').val();
    var srcRegion = $('#srcRegion').val();
	var tgtRegion = $('#tgtRegion').val();

    if((srcRegion === '') || (tgtRegion === '')){
        alert('尚未开通该国的转译服务');
        return;
    }

    if((srcResult === '')){
        alert('请选择一个！');
        return;
    }

	$('#tgtResultTable tbody').html("");
	$("#tgtResultTable tbody").append("<tr><th >转译中...</th><th></th><th></th><th></th><th></th></tr>");


					
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

            $('#tgtResultTable tbody').html("");
        	for(var i=0; i<data.length; i++) {
                var hscode = data[i]['hscode'];
                var description = data[i]['description'];
                var added = data[i]['added'];
                var general = data[i]['general'];
                var favor = data[i]['favor'];
                console.log(hscode+description);
                $('#tgtResultTable tbody').append("<tr><th>"+hscode+"</th><th>"+description+"</th><th>"+added+"</th><th>"+general+"</th><th>"+favor+"</th></tr>");
            }
        }
    });
};

function reset() {
	$('#form').get(0).reset();
	
}
