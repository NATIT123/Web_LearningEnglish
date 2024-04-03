
$(document).ready(function() {
	var simplemde;
	
	//default. load all object baiGrammar
	window.onload = function () {
		
		// create markdown
		 simplemde =new SimpleMDE({
		    element: document.getElementById("markdown-editor"),
		    spellChecker: false,
		});
		
	};
});
