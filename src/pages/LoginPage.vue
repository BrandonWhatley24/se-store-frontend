<template>
  <v-container fluid>
    <v-row>
      <v-col cols="3">
      </v-col>
      <v-col cols="6" align="center" justify="center">
        <v-card elevation="3" outlined width="50%">
          <v-card-title class="justify-center">Login</v-card-title>
          <v-col cols="1"></v-col>
          <v-col cols="10">
            <v-text-field v-model="username" label="Username" filled></v-text-field>
            <v-text-field v-model="password" type="password" label="Password" filled></v-text-field>
            <v-btn block @click="loginFunction">
              Login
            </v-btn>
          </v-col>
          <v-col cols="1"></v-col>
        </v-card>
      </v-col>
      <v-col cols="3">
      </v-col>
    </v-row>
  </v-container>
</template>
  
<script>
export default {
  name: 'HelloWorld',

  data: () => ({
    username: "",
    password: "",
    savedUsers: [
      {username: "TestUser", password: "userTestPass"}
    ],
  }),
  methods: {
    loginFunction(){
      let username = this.username;
      let password = this.password;
      let userFound = false;

      for(let i = 0; i < this.savedUsers.length; i++){
        if(this.savedUsers[i].username.toUpperCase === username.toUpperCase && password === this.savedUsers[i].username){
          userFound = true;
        }
      }

      if(userFound){
        console.log()
      }
    }
  }
}
</script>
  