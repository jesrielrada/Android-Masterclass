package com.example.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    /**
     * Cold flow - would not emit values if no collectors
     */

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    /**
     * Hot flow - State Flow - is for state the you want to retain after screen rotation
     * SharedFlow is use to send one time event
     */

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()


    private val _sharedFlow = MutableSharedFlow<Int>(replay = 5)
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun squareNumber(number: Int){
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }

    fun incrementCounter() {
        _stateFlow.value += 1

    }

    init {
        //collectFlow()
        //collectFlowV2()
        //collectFlowWithFilter()
        //collectFlowWithFilterAndMap()
        //collectFlowWithOnEach()
        //collectFlowTerminalOperatorCount()
        //collectFlowTerminalOperatorReduce()
        //collectFlowTerminalOperatorFold()
        //collectFlowTerminalOperatorFlatMapConcat()
        squareNumber(3)

        viewModelScope.launch {
            sharedFlow.collect{
                delay(2000L)
                println("FIRST FLOW: The received number is $it")
            }
        }
        viewModelScope.launch {
            sharedFlow.collect{
                delay(3000L)
                println("SECOND FLOW: The received number is $it")
            }
        }

    }

    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow.collect { time ->
                println("The current time is $time")
            }
        }
    }

    /**
     * Similar output on top but cleaner implementation
     */
    private fun collectFlowV2() {
        countDownFlow.onEach { time ->
            println("The current time is $time")
        }.launchIn(viewModelScope)
    }


    private fun collectFlowWithFilter() {
        viewModelScope.launch {
            countDownFlow
                .filter { time ->
                    time % 2 == 0
                }.collect { time ->
                    println("The current filtered time is $time")
                }
        }
    }

    private fun collectFlowWithFilterAndMap() {
        viewModelScope.launch {
            countDownFlow
                .filter { time ->
                    time % 2 == 0
                }.map { time ->
                    time * time
                }
                .collect { time ->
                    println("The current mapped time is $time")
                }
        }
    }

    private fun collectFlowWithOnEach() {
        viewModelScope.launch {
            countDownFlow
                .filter { time ->
                    time % 2 == 0
                }.map { time ->
                    time * time
                }.onEach {
                    println("The current onEach time is $it")
                }
                .collect { time ->
                    println("The current mapped time is $time")
                }
        }
    }

    private fun collectFlowTerminalOperatorCount() {
        viewModelScope.launch {
            val count = countDownFlow
                .filter { time ->
                    time % 2 == 0
                }.map { time ->
                    time * time
                }.onEach {
                    println("The current onEach time is $it")
                }
                .count {
                    it % 2 == 0
                }

            println("the count is $count")
        }
    }

    private fun collectFlowTerminalOperatorReduce() {
        viewModelScope.launch {
            val reduceResult = countDownFlow.reduce { accumulator, value ->
                accumulator + value
            }

            println("The count is $reduceResult")
        }
    }

    private fun collectFlowTerminalOperatorFold() {
        viewModelScope.launch {
            val foldResult = countDownFlow.fold(100) { accumulator, value ->
                accumulator + value
            }

            println("The fold is $foldResult")
        }
    }

    // [[1,2], [1,2,3]]
    // [1, 2, 1, 2, 3]
    private fun collectFlowTerminalOperatorFlatMapConcat() {
        val flow1 = flow {
            emit(1)
            delay(500L)
            emit(2)
        }

        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }.collect { value ->
                println("The value is $value")
            }
        }
    }

}