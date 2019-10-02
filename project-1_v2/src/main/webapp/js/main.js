const NAVBAR = document.getElementById('navbar');
const APP_VIEW = document.getElementById('app-view');
const DYNAMIC_CSS = document.getElementById('dynamic-css');

window.onload = function() {

    console.log('Initializing application...');

    console.log('Loading components and services...')
    const ROUTER = new Router();
    const AUTH_SERVICE = new AuthService(ROUTER);
    const LOGIN_COMPONENT = new LoginComponent(AUTH_SERVICE, ROUTER);
    const REGISTER_COMPONENT = new RegisterComponent(AUTH_SERVICE, ROUTER);
    const DASHBOARD_COMPONENT = new DashboardComponent(AUTH_SERVICE, ROUTER)
    const NAVBAR_COMPONENT = new NavbarComponent(ROUTER);
    console.log('Components and services and loaded.');

    console.log('Populating routes...');
    ROUTER.addRoute('login', LOGIN_COMPONENT);
    ROUTER.addRoute('register', REGISTER_COMPONENT);
    ROUTER.addRoute('dashboard', DASHBOARD_COMPONENT);
    console.log('Routes populated.');

    NAVBAR_COMPONENT.render();
    
}

//-----------------------------------------------------------

// Components

class LoginComponent {

    template=`
    <div class="login-form">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <label for="username-cred" class="sr-only">Username</label>
        <input type="text" id="username-cred" class="form-control" placeholder="Username" required autofocus>
        
        <label for="password-cred" class="sr-only">Password</label>
        <input type="password" id="password-cred" class="form-control" placeholder="Password" required>
        
        <button class="btn btn-lg btn-primary btn-block" id="submit-creds">Sign in</button>
        <br>
        <div hidden class="alert alert-danger text-center" id="alert-msg" role="alert">
            Invalid Credentials!
        </div>
        <p class="mt-5 mb-3 text-muted">&copy; Revature 2019</p>
    </div>
    `;

    constructor(authService, router) {
        console.log('Instantiating LoginComponent');
        this.authService = authService;
        this.router = router;
        console.log('LoginComponent instantiation complete.');
    }

    render = () => {
        console.log('Rendering LoginComponent template...');
        APP_VIEW.innerHTML = this.template;
        document.getElementById('submit-creds').addEventListener('click', this.login);
        console.log('LoginComponent rendered.');
    }

    login = async () => {
        let username = document.getElementById('username-cred').value;
        let password = document.getElementById('password-cred').value;

        let user = await this.authService.authenticate(username, password);

        if(user) {
            console.log('Authentication successful!');
            document.getElementById('alert-msg').setAttribute('hidden', true);
            console.log(this.authService.currentUser);
            // route to dashboard component here.
            this.router.fetchComponent('dashboard').render();



        } else {
            document.getElementById('alert-msg').removeAttribute('hidden');

        }
    }

}

class DashboardComponent {

    employee_template = 
     `
    <h2>Welcome to Revature Reimbursement</h2>

    <div class="create-reimbursement-container">
        <h5>Create Reimbursement</h5>
        <form id="create-reimbursement-form" class="form">
            <div>
                <input type="number" name="amount" placeholder="amount" />
            </div>
            <div>
                <textarea id="description" placeholder="description"></textarea>
            </div>
            <div>
                <input type="file" name="pic" accept="image/*" />
            </div>
            <div>
                <select id="reimbursement-types" name="reimbursement-types">
                    <option value="1">lodging</option>
                    <option value="2">travel</option>
                    <option value="3">food</option>
                    <option value="4">other</option>
                </select>
            </div>
            <div>

            </div>
            
            <input type="submit" name="submit" id="submit-reimbursement" />
        </form>
    </div>
    <div hidden id="success">Reimbursement Created</div>
    <div hidden id="failed">Reimbursent submission failed</div>
    <br><br>

    <button id="see-My-reimbursements">See My Reimbursements</button>
    <button id="see-all-reimbursements">Manager See All Reimbursements</button>
    `//

    finance_template = ''//

    constructor(authService, router) {
        console.log('in dashboard constructor');
        this.authService = authService;
        this.router = router;
    }


createReimbursement = async (e) => {
    debugger
    let formData = {
        reimb_amount: '',
        reimb_description: '',
        reimb_receipt: '',
        reimb_author: this.currentUser,
        reimb_status: 'pending',
        reimb_type: ''
    };

    let response = await fetch('auth', {
        method: 'POST',
        
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('auth')
        },
        body: formData
    }).catch(err => console.log(err));

    if(response.status == 400) {
        console.log('Invalid request to server.'); 
        return null;
    }

    if(response.status == 401) {
        console.log('Invalid credentials provided, no user found');
        return null;
    }

    if(response.status == 500) {
        console.log('Internal server error.');
        return null;
    }
    


}
render = () => {
    console.log('rendering dashboard')
    if(this.authService.currentUser.role === 'Employee') {
        APP_VIEW.innerHTML = this.employee_template;
        console.log('rendering emoloyee template')
        //attach listeners
            //first to the form submission to create reimbursements
            //second to get all the users reimbursements
    }

    if(this.authService.currentUser.role === 'finance') {
        console.log('rendering finance template');
        APP_VIEW.innerHTML = this.finance_template;
        //attach listeners
    }
}




}

class RegisterComponent {
template = ``






constructor(authService, router) {
    this.authService = authService;
    this.router = router;
}
}

class NavbarComponent {
    template = `
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" id="to-dashboard" href="#">Reimburse</a>
        
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#app-nav" aria-controls="app-nav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    
        <div class="collapse navbar-collapse" id="app-nav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#" id="toHome">Home<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item dropdown"><a
                    class="nav-link dropdown-toggle" id="dropdown01" 
                    data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="true"><span id="dropdown-label">Pages</span></a>
                    <div class="dropdown-menu" id="nav-dropdown" aria-labelledby="dropdown01">
                        <a class="dropdown-item" id="to-login" href="#">Login</a> 
                        <a class="dropdown-item" id="to-register" href="#">Register</a>
                    </div>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
    `;

    constructor(router) {
        console.log('Instantiating NavbarComponent...');
        this.router = router;
        console.log('NavbarComponent instantiation complete.')
    }



    render = () => {
        console.log('Rendering NavbarComponent template...');
        NAVBAR.innerHTML = this.template;
        document.getElementById('to-login').addEventListener('click', this.router.fetchComponent('login').render);
        //if they click the register link then they will be routed to register component
        //document.getElementById('register').addEventListener('click', this.router.fetchComponent('register').render);
        console.log("login successful!")
        console.log('Rendering complete.')
    }

}

//-----------------------------------------------------------------

// Services

class AuthService {

    currentUser;

    constructor(router) {
        console.log('Instantiating AuthService...');
        this.router = router;
        this.currentUser = new User();
        console.log('AuthService instantiation complete.');
    }

    authenticate = async (username, password) =>{
        let credentials = {
            username: username,
            password: password
        };

        let response = await fetch('auth', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(credentials)
        }).catch(err => console.log(err));

        if(response.status == 400) {
            console.log('Invalid request to server.'); 
            return null;
        }

        if(response.status == 401) {
            console.log('Invalid credentials provided, no user found');
            return null;
        }

        if(response.status == 500) {
            console.log('Internal server error.');
            return null;
        }
        
        // Persisting the JWT to localStorage
        localStorage.setItem('rbs-jwt', response.headers.get('Authorization'));

        let data = await response.json();
        this.currentUser = data;
        
        return data;
        
    }

    logout = () => {
        localStorage.removeItem('reimb-jwt');
        this.router.fetchComponent('login').render();
    }

}

class Router {

    routes = [];

    constructor() {
        console.log('Instantiating Router...');
        console.log('Router instantiation complete.')
    }

    addRoute = (path, component) => {
        this.routes.push({path, component})
    }

    fetchComponent = (path) => {
        let destinationRoute = this.routes.filter(route => route.path === path).pop();
        return destinationRoute.component;
    }

}

//----------------------------------------------------------------

// Models

class User {
    constructor(firstName, lastName, username, password, email, role, id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.id = id;
    }
}


