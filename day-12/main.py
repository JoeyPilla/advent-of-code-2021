data = [line for line in open("test-input.txt").read().strip().split("\n")]

mapping = {}
for l in data:
    sp = l.split('-')
    if sp[0] in mapping:
        mapping[sp[0]].append(sp[1])
    else:
        mapping[sp[0]] = [sp[1]]
    if sp[1] in mapping:
        mapping[sp[1]].append(sp[0])
    else:
        mapping[sp[1]] = [sp[0]]

def traverse(current, arr = [], res = [], small_tried = False):
    n = arr[:]
    n.append(current)

    if current == 'end':
        res.append(n)
        return res

    if current and current in mapping:
        for path in mapping[current]:
            if path == "start":
                continue
            if path.islower() and path in arr:
                if small_tried:
                    continue
                traverse(path, n, res, True)
            traverse(path, n, res, False)

    return res


print(len(traverse('start')))
