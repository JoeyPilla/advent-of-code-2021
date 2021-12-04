package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
)

func getInput() []string {
	bytes, err := ioutil.ReadFile("input.txt")
	if err != nil {
		return []string{}
	}
	contents := string(bytes)
	split := strings.Split(contents, "\n")
	return split[:len(split)-1]
}

func getOneCount(arr []string) (int, []int) {
	count := 1
	oneCount := make([]int, len(arr[0]))

	for i, line := range arr {
		for j, ele := range line {
			if string(ele) == "1" {
				oneCount[j] += 1
			}
		}
		count = i
	}

	return count, oneCount
}

func getGammaAndEps(input []string) (string, string) {
	totalRows, oneCount := getOneCount(input)

	gamma := ""
	eps := ""

	for _, ele := range oneCount {
		if totalRows/2 < ele {
			gamma += "1"
			eps += "0"
		} else {
			gamma += "0"
			eps += "1"
		}
	}

	return gamma, eps
}

func calculateGammaAndEps(input []string) int64 {
	gamma, eps := getGammaAndEps(input)

	gammaNum, _ := strconv.ParseInt(gamma, 2, 64)
	epsNum, _ := strconv.ParseInt(eps, 2, 64)

	return gammaNum * epsNum
}

func filter(arr []string, oneCount []int, totalRows, col int) []string {
	newArr := []string{}

	for _, line := range arr {
		if totalRows/2 < oneCount[col] {
			if string(line[col]) == "1" {
				newArr = append(newArr, line)
			}
		} else {
			if string(line[col]) == "0" {
				newArr = append(newArr, line)
			}
		}
	}
	return newArr
}
func filter2(arr []string, oneCount []int, totalRows, col int) []string {
	newArr := []string{}

	for _, line := range arr {
		if totalRows/2 < oneCount[col] {
			if string(line[col]) == "0" {
				newArr = append(newArr, line)
			}
		} else {
			if string(line[col]) == "1" {
				newArr = append(newArr, line)
			}
		}
	}
	return newArr
}

func getValue(input []string, filter func([]string, []int, int, int) []string) int64 {
	col := 0

	for len(input) != 1 {
		totalRows, oneCount := getOneCount(input)
		input = filter(input, oneCount, totalRows, col)

		col = (col + 1) % len(input[0])
	}

	num, _ := strconv.ParseInt(input[0], 2, 64)
	return num
}

func getOxygen(input []string) int64 {
	return getValue(input, filter)
}

func getCO2(input []string) int64 {
	return getValue(input, filter2)
}

func main() {
	input := getInput()

	fmt.Println(calculateGammaAndEps(input))

	fmt.Println(getOxygen(input) * getCO2(input))
}
