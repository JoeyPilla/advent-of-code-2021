count = 0

def get_input():
    input = []
    with open('input.txt') as f:
        line = f.readline()
        while line:
            input.append(int(line))
            line = f.readline()
    return input

def part_1(inputs):
    count = 0
    for i, number in enumerate(inputs):
        if i == 0:
            continue
        if number > inputs[i-1]:
            count += 1
    return count

def part_2(inputs):
    count = 0
    previous = 0
    for i, number in enumerate(inputs):
        if i < 3:
            previous += number
            continue

        current = inputs[i] + inputs[i-1] + inputs[i-2]

        if current > previous:
            count += 1

        previous = current
    return count

inputs = get_input()

print(part_1(inputs))
print(part_2(inputs))
