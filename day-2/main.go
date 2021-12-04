package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
)

type command struct {
	instruction string
	value       int
	vertical    int
}

func createCommand(str string) *command {
	s := strings.Split(str, " ")
	val, _ := strconv.Atoi(s[1])

	return &command{
		instruction: s[0],
		value:       val,
	}
}

type submarine struct {
	horizontal int
	vertical   int
	aim        int
}

func createSubmarine() *submarine {
	return &submarine{
		horizontal: 0,
		vertical:   0,
		aim:        0,
	}
}

func (sub *submarine) forward(pos int) {
	sub.horizontal += pos
	sub.vertical += pos * sub.aim
}

func (sub *submarine) up(pos int) {
	sub.aim -= pos
}

func (sub *submarine) down(pos int) {
	sub.aim += pos
}

func (sub *submarine) processCommand(c command) {
	switch c.instruction {
	case "forward":
		sub.forward(c.value)
	case "up":
		sub.up(c.value)
	case "down":
		sub.down(c.value)
	}
}

func (sub *submarine) processInput(input []string) {
	for _, str := range input {
		c := *createCommand(str)
		sub.processCommand(c)
	}
}

func getInput() []string {
	bytes, err := ioutil.ReadFile("input.txt")
	if err != nil {
		return []string{}
	}
	contents := string(bytes)
	split := strings.Split(contents, "\n")
	return split[:len(split)-1]
}

func answer(sub submarine) int {
	return sub.horizontal * sub.vertical
}

func main() {
	input := getInput()
	sub := createSubmarine()
	sub.processInput(input)
	fmt.Println(answer(*sub))
}
