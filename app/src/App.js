import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'; // mimics server allowing back, forward page routing etc
import Landing_page from './components/layout/Landing_page';
import ErrorComponent from './components/layout/ErrorComponent';

class App extends Component {
    render() {
        return (
                <Router>
                    <div className="App">
                        <Switch>
                            <Route exact path="/" component={Landing_page}/>
                            <Route component={ErrorComponent}/>
                        </Switch>
                    </div>
                </Router>
        );
    }
}

export default App;
