dataset =[[1, 1, 1, 1, 3], [1, 1, 1, 2, 2], [1, 1, 2, 1, 3], [1, 1, 2, 2, 1], [1, 2, 1, 1, 3], [1, 2, 1, 2, 2], [1, 2, 2, 1, 3], [1, 2, 2, 2, 1], [2, 1, 1, 1, 3], [2, 1, 1, 2, 2], [2, 1, 2, 1, 3], [2, 1, 2, 2, 1], [2, 2, 1, 1, 3], [2, 2, 1, 2, 2], [2, 2, 2, 1, 3], [2, 2, 2, 2, 3], [3, 1, 1, 1, 3], [3, 1, 1, 2, 3], [3, 1, 2, 1, 3], [3, 1, 2, 2, 1], [3, 2, 1, 1, 3], [3, 2, 1, 2, 2], [3, 2, 2, 1, 3], [3, 2, 2, 2, 3]]

var set  =function(list){
	var set  = []
	for(x in list){
		if(set.indexOf(x)==-1){
			set.push(x)	
		}
	}
	return set
}



var entropy = function(dataset) {
	labels =set()
    label_dict = 
    for label in labels:
        label_dict[label] += 1
    h = 0.0
    for k, v in label_dict.iteritems():
        h -= v / len(labels) * log(v / len(labels), 2)
    return h

}


var buildDecisionTree = function(dataset, target_attributes, attributes) {	
	var root = {
		leaf: false
	};
	for (var index = 0; index < target_attributes.length; index++) {
		if (ArrayList.filter(target_attributes[index], dataset).length == dataset.length) {
			root.leaf = true;
			root.label = target_attributes[index].value;
			return root;
		}
	}

	if (attributes.length == 0) {
		root.leaf = true;
		root.label = ArrayList.mostCommonTargetAttributeName(target_attributes, dataset).value;
		return root
	}
	root.attribute = ArrayList.magicAttribute(attributes, dataset);
	root.children = [];
	var optionsOfSpecificAtrribute = attributes.filter(function(item) {
		return item.name == root.attribute;
	}).pop().value;

	for (var index in optionsOfSpecificAtrribute) {
		var option = optionsOfSpecificAtrribute[index];
		var datasetWithSpecificOption = dataset.filter(function(example) {
			if (option instanceof Interval) {
				return option.contains(example[root.attribute]);
			} else
				return option == example[root.attribute];
		});
		//如果datasetWithSpecificOption集合为空，分支为一个叶子节点
		if (datasetWithSpecificOption.length == 0) {
			var node = {
				leaf: true
			};
			node.label = ArrayList.mostCommonTargetAttributeValue(target_attributes, dataset).value;
			root.children.push(node);
		} else {
			var attrs = attributes.filter(function(attribute) {
				return attribute.name != root.attribute;
			});
			//将datasetWithSpecificOption用除了root.attribute之外的其他属性来构建子树，作为root的分支
			root.children.push(buildDecisionTree(datasetWithSpecificOption, target_attributes, attrs));
		}
	};

	return root;
}

var ArrayList = {};

//在attributes中找到一个最能分类dataset的属性
ArrayList.magicAttribute = function(attributes, dataset) {
	return attributes[0].name;
}

// 这个一个属性列表，包含名称和对应的值， 这些值可以是离散，也可能是连续的，连续的值可以用一个interval object来表示
var Interval = (function() {
	function Interval(from, to) {
		this.from = from;
		this.to = to;
	}
	Interval.of = function(from, to) {
		return new Interval(from, to);
	}
	//用来判断某个离散的值，是否属于这个区间
	Interval.prototype.contains = function(value) {
		return value > this.from && value <= this.to;
	}
	return Interval;
})();

// 样例包含的所有属性和属性的取值
var attributes = [{
	name: 'outlook',
	value: ['sunny', 'overcast', 'rain']
}, {
	name: 'temperature',
	value: [Interval.of(0, 50), Interval.of(50, 60), Interval.of(60, 70), Interval.of(70, 80), Interval.of(80, 90), Interval.of(90, 100)]
}, {
	name: 'humidity',
	value: [Interval.of(0, 50), Interval.of(50, 60), Interval.of(60, 70), Interval.of(70, 80), Interval.of(80, 90), Interval.of(90, 100)]
}, {
	name: 'windy',
	value: [true, false]
}];

ArrayList.filter = function(condition, source) {
	return source.filter(function(item) {
		return item[condition.name] == condition.value
	});
}

var target_attributes = [{
	name: 'play',
	value: true
}, {
	name: 'play',
	value: false
}];

ArrayList.mostCommonTargetAttributeValue = function(attributes, dataset) {
	return target_attributes.reduce(function(result, item) {
		if (ArrayList.filter(result, dataset).length > ArrayList.filter(item, dataset).length) {
			return result;
		} else {
			return item;
		}
	});
}



//准备一组14个样例，样例来自维基百科决策树
var dataset = [{
	outlook: "sunny",
	temperature: 85,
	humidity: 85,
	windy: false,
	play: false
}, {
	outlook: "sunny",
	temperature: 80,
	humidity: 90,
	windy: true,
	play: false
}, {
	outlook: "overcast",
	temperature: 83,
	humidity: 78,
	windy: false,
	play: true
}, {
	outlook: "rain",
	temperature: 70,
	humidity: 96,
	windy: false,
	play: true
}, {
	outlook: "rain",
	temperature: 68,
	humidity: 80,
	windy: false,
	play: true
}, {
	outlook: "rain",
	temperature: 65,
	humidity: 70,
	windy: true,
	play: false
}, {
	outlook: "overcast",
	temperature: 64,
	humidity: 65,
	windy: true,
	play: true
}, {
	outlook: "sunny",
	temperature: 72,
	humidity: 95,
	windy: false,
	play: false
}, {
	outlook: "sunny",
	temperature: 69,
	humidity: 70,
	windy: false,
	play: true
}, {
	outlook: "rain",
	temperature: 75,
	humidity: 80,
	windy: false,
	play: true
}, {
	outlook: "sunny",
	temperature: 75,
	humidity: 70,
	windy: true,
	play: true
}, {
	outlook: "overcast",
	temperature: 72,
	humidity: 90,
	windy: true,
	play: true
}, {
	outlook: "overcast",
	temperature: 81,
	humidity: 90,
	windy: true,
	play: true
}, {
	outlook: "rain",
	temperature: 71,
	humidity: 80,
	windy: true,
	play: false
}]


// 对数基变换公式  logx(y) =  ln(y)/ln(x)

var log = function(base, x) {
	return Math.log(x) / Math.log(base);
}

var S = [{
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": 1
}, {
	"output": -1
}, {
	"output": -1
}, {
	"output": -1
}, {
	"output": -1
}, {
	"output": -1
}];

