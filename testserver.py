from http.server import SimpleHTTPRequestHandler
from http.server import HTTPServer
import json
from urllib.parse import parse_qs


ENVIRONMENTS = ["STA", "SPT", "PROD"]

is_running = False


users = [{
    "id": 1,
    "firstname": "Lukas",
    "lastname": "Meisegeier",
    "city": "Berlin",
    "siblings": ["Marius", "Melissa"],
    "trivia": "Lorem ipsum"}]


class MyHandler(SimpleHTTPRequestHandler):

    def unlock(self):
        MyHandler.is_running = False

    def do_GET(self):
        global users
        self.send_response(200)
        self.send_header("Content-type", "text/json")
        self.end_headers()
        meta = {}
        with open("meta_list.json", "r") as f:
            meta = json.loads(f.read())
        result = {'data': {"users": users}, "meta": meta}
        self.wfile.write(bytes(json.dumps(result), "utf-8"))

    def do_POST(self):
        global users
        data = self.rfile.read(int(self.headers['Content-Length'])).decode("utf-8")
        params = parse_qs(data)
        if 'json' in params:
            action = json.loads(params['json'][0])
            if action['name'] == 'details':
                return self.open_details(action)
            if action['name'] == 'add':
                return self.add_user(action)
            if action['name'] == 'save':
                return self.save_user(action)
            if action['name'] == 'delete':
                return self.delete_user(action)

        self.send_response(200)
        self.send_header("Content-type", "text/json")
        self.end_headers()
        meta = {}
        with open("meta_list.json", "r") as f:
            meta = json.loads(f.read())
        result = {'data': {"users": users}, "meta": meta}
        self.wfile.write(bytes(json.dumps(result), "utf-8"))

    def open_details(self, action):
        user = action['fields']['users']
        for user_entry in users:
            if str(user_entry['id']) == user['id']:
                self.send_response(200)
                self.send_header("Content-type", "text/json")
                self.end_headers()
                meta = {}
                with open("meta_detail.json", "r") as f:
                    meta = json.loads(f.read())

                result = {'data': user_entry, "meta": meta}
                self.wfile.write(bytes(json.dumps(result), "utf-8"))
                break

    def add_user(self, action):
            max_id = 0
            for user in users:
                if user['id'] > max_id:
                    max_id = user['id']
            new_id = max_id + 1
            self.send_response(200)
            self.send_header("Content-type", "text/json")
            self.end_headers()
            meta = {}
            with open("meta_detail.json", "r") as f:
                meta = json.loads(f.read())
            empty_user = {'id': new_id, 'firstname': "", 'lastname': '', 'city': '', 'siblings': [], 'trivia': ""}

            result = {'data': empty_user, "meta": meta}
            self.wfile.write(bytes(json.dumps(result), "utf-8"))

    def save_user(self, action):
        fields = ['id', 'firstname', 'lastname', 'city', 'siblings', 'trivia']
        user = {}
        for field in fields:
            user[field] = action['fields'].get(field, "")
        user['id'] = int(user['id'])

        indexFound = None
        i = 0
        for old_user in users:
            if str(old_user['id']) == str(user['id']):
                indexFound = i
            i += 1
        if indexFound is not None:
            users[indexFound] = user
        else:
            users.append(user)

        self.send_response(200)
        self.send_header("Content-type", "text/json")
        self.end_headers()
        meta = {}
        with open("meta_list.json", "r") as f:
            meta = json.loads(f.read())
        result = {'data': {"users": users}, "meta": meta}
        self.wfile.write(bytes(json.dumps(result), "utf-8"))

    def delete_user(self, action):
        user = action['fields']['users']
        found = False
        userFound = None
        for user_entry in users:
            if str(user_entry['id']) == user['id']:
                found = True
                userFound = user_entry
                break
        if found:
            users.remove(userFound)
        self.send_response(200)
        self.send_header("Content-type", "text/json")
        self.end_headers()
        meta = {}
        with open("meta_list.json", "r") as f:
            meta = json.loads(f.read())
        result = {'data': {"users": users}, "meta": meta}
        self.wfile.write(bytes(json.dumps(result), "utf-8"))


server = HTTPServer(('', 8001), MyHandler)
server.serve_forever()
