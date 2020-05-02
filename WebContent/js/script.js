function overlayReqOn(id) {
	var overlay = document.getElementById("overlay-req");
	var flightId = document.getElementById("flight-id");
	console.log(flightId);
	flightId.setAttribute("value", id);

	overlay.style.display = 'block';
}

function overlayOn(id) {
	var overlay = document.getElementById(id);
	overlay.style.display = 'block';
}

function overlayOff(id) {
	var overlay = document.getElementById(id);
	overlay.style.display = 'none';
}

var JSON_REQUEST;
function showCarList(jsonCars, jsonRequest) {
	var carList = document.getElementById("overlay-car-list");

	var model = document.getElementById("car-model");
	model.textContent = jsonRequest['carModelName'];

	var enginePower = document.getElementById("car-engine-power");
	enginePower.textContent = jsonRequest['carEnginePower'];

	var engineType = document.getElementById("car-engine-type");
	engineType.textContent = jsonRequest['carEngineTypeName'];

	var numOfSeats = document.getElementById("car-num-of-seats");
	numOfSeats.textContent = jsonRequest['carNumOfSeats'];

	JSON_REQUEST = jsonRequest;

	filterCarList();

	carList.style.display = 'block';
}

function filterCarList() {
	var carRows = document.getElementsByClassName('car-row');

	var epRange = parseInt(document.getElementById("ep-range").value);
	
	var hasOneRow = false;
	for (var i = 0; i < carRows.length; i++) {
		var row = carRows[i];
		
//		assign car for a flight
		var inputFlightId = row.getElementsByClassName("flight-id")[0];
		var inputUserId = row.getElementsByClassName("user-id")[0];
		var inputRequestId = row.getElementsByClassName("request-id")[0];
		inputFlightId.value = JSON_REQUEST['flightId'];
		inputUserId.value = JSON_REQUEST['userId'];
		inputRequestId.value = JSON_REQUEST['id'];
		
		var carColumns = row.getElementsByTagName('td');

		var maxEPower = parseInt(JSON_REQUEST['carEnginePower']) + epRange;
		var minEPower = parseInt(JSON_REQUEST['carEnginePower']) - epRange;

		if (carColumns[1].textContent == JSON_REQUEST['carModelName']
				&& (carColumns[2].textContent >= minEPower && carColumns[2].textContent <= maxEPower)
				&& (carColumns[3].textContent == JSON_REQUEST['carEngineTypeName'])
				&& (carColumns[4].textContent == JSON_REQUEST['carNumOfSeats'])) {
			hasOneRow = true;
			row.style.display = 'table-row';
		} else {
			row.style.display = 'none';
		}
	}
	
	var table = document.getElementById('overlay-car-list-table');
	if(!hasOneRow) {
		table.style.display = 'none';
	} else {
		table.style.display = 'block';
	}
}

function addCar(buttonName) {
	var inputCommand = document.getElementById('command');
	inputCommand.value = 'addCar';
	
	var form = document.getElementById('form');
	var inputCarId = document.getElementById('car-id');
	var inputCarEnginePower = document.getElementById('car-engine-power');

	if(form) {
		var prop = form.getElementsByClassName('prop')[0];
		prop.style.display = 'none';
		if(inputCarId) {
			form.removeChild(inputCarId);
		}
		
		inputCarEnginePower.value = '100';
	}
	
	var submitBut = document.getElementById('submit-button');
	submitBut.innerText = buttonName;
	console.log(submitBut.innerText);
	
	overlayOn('overlay-car');
}

function changeCar(carId, carEnginePower, buttonName) {
	var inputCommand = document.getElementById('command');
	inputCommand.value = 'changeCar';

	var form = document.getElementById('form');
	var inputCarId = document.getElementById('car-id');
	var inputCarEnginePower = document.getElementById('car-engine-power');
	
	if(form) {
		if(!inputCarId) {
			var hiddenInputCarId = document.createElement('input');
			hiddenInputCarId.id='car-id';
			hiddenInputCarId.type = 'hidden';
			hiddenInputCarId.name = 'carId';
			hiddenInputCarId.value = carId;
			form.appendChild(hiddenInputCarId);
		} else {
			inputCarId.value = carId;
		}
		
		inputCarEnginePower.value = carEnginePower;
		
		
		var prop = form.getElementsByClassName('prop')[0];
		var spans = prop.getElementsByTagName('span');
		prop.style.display = 'flex';
		spans[1].innerText = carId;
		
	}
	
	var submitBut = document.getElementById('submit-button');
	submitBut.innerText = buttonName;
	
	overlayOn('overlay-car');
}